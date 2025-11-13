package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.Security.LoginRequestDTO;
import com.example.FaceShield_Back.DTO.Security.RegisterRequestDTO;
import com.example.FaceShield_Back.DTO.Security.ResponseDTO;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Repository.UsuariosRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuariosRepo repository;
    private final PasswordEncoder passwordEncoder;

    // REMOVEMOS o TokenService - não vamos mais usar JWT

    /**
     * Endpoint de LOGIN - Agora usando Basic Auth do Spring Security
     * O Spring vai automaticamente validar as credenciais
     * Este endpoint só verifica se o usuário existe e retorna informações básicas
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        // 1. Encontra o usuário pelo username
        Usuarios user = this.repository.findByUsername(body.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Verifica o Tipo de Usuário para decidir a lógica de validação
        if ("PROFESSOR".equalsIgnoreCase(user.getTipoUsuario())) {
            // LÓGICA PARA PROFESSOR: Precisa validar a senha
            if (body.senha() == null || !passwordEncoder.matches(body.senha(), user.getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida para professor.");
            }
        } else if ("ALUNO".equalsIgnoreCase(user.getTipoUsuario())) {
            // LÓGICA PARA ALUNO: Não valida senha, só a existência do username
            // Não faz nada - apenas permite o login
        } else {
            return ResponseEntity.badRequest().body("Tipo de usuário desconhecido.");
        }

        // 3. Retorna informações do usuário SEM TOKEN
        // O Spring Security vai cuidar da autenticação via Basic Auth
        return ResponseEntity.ok(new ResponseDTO(
                user.getId(),
                user.getNome(),
                "Login realizado com sucesso - Use Basic Auth para requisições"
        ));
    }

    /**
     * Endpoint de REGISTRO (ATUALIZAÇÃO por ID)
     * Mantemos a mesma lógica, mas sem gerar token
     */
    @PutMapping("/register/{id}")
    public ResponseEntity<?> register(@PathVariable Long id, @RequestBody RegisterRequestDTO body) {

        // 1. Encontra o usuário EXISTENTE pelo ID
        Optional<Usuarios> userToUpdateOpt = this.repository.findById(id);

        // 2. Verifica se o usuário foi encontrado
        if (userToUpdateOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado. O cadastro facial (Python) não foi localizado.");
        }

        Usuarios userToUpdate = userToUpdateOpt.get();

        // 3. Verifica se este usuário já não completou seu registro
        if (userToUpdate.getUsername() != null && !userToUpdate.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Este usuário já completou seu registro anteriormente.");
        }

        // 4. Verifica se o USERNAME já não foi usado por outra pessoa
        Optional<Usuarios> existingUserWithThisUsername = this.repository.findByUsername(body.username());
        if (existingUserWithThisUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Este 'username' já está em uso. Por favor, escolha outro.");
        }

        // 5. ATUALIZA o usuário existente com os novos dados
        userToUpdate.setUsername(body.username());

        // 6. Lógica da Senha (usando o tipo de usuário do BANCO DE DADOS)
        String tipoUsuarioDoBanco = userToUpdate.getTipoUsuario();

        if ("PROFESSOR".equalsIgnoreCase(tipoUsuarioDoBanco)) {
            // Se for professor, a senha vinda do BODY é obrigatória
            if (body.senha() == null || body.senha().isEmpty()) {
                return ResponseEntity.badRequest().body("Senha é obrigatória para PROFESSOR.");
            }
            userToUpdate.setSenha(passwordEncoder.encode(body.senha()));

        } else if ("ALUNO".equalsIgnoreCase(tipoUsuarioDoBanco)) {
            // Se for aluno, a senha é SEMPRE nula
            userToUpdate.setSenha(null);

        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Tipo de usuário inconsistente no banco de dados.");
        }

        // 7. Salva as ATUALIZAÇÕES no banco
        this.repository.save(userToUpdate);

        // 8. Retorna sucesso SEM TOKEN
        return ResponseEntity.ok(new ResponseDTO(
                userToUpdate.getId(),
                userToUpdate.getUsername(),
                "Registro completado com sucesso - Use Basic Auth para login"
        ));
    }
}