package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.Security.LoginRequestDTO;
import com.example.FaceShield_Back.DTO.Security.RegisterRequestDTO;
import com.example.FaceShield_Back.DTO.Security.ResponseDTO;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Repository.UsuariosRepo;
import com.example.FaceShield_Back.Service.Security.TokenService; // Verifique se esta importação está correta
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuariosRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    /**
     * Endpoint de LOGIN
     * Agora diferencia o login de ALUNO (sem senha) e PROFESSOR (com senha).
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        // 1. Encontra o usuário pelo username
        Usuarios user = this.repository.findByUsername(body.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token; // Variável para armazenar o token gerado

        // 2. Verifica o Tipo de Usuário para decidir a lógica de login
        if ("PROFESSOR".equalsIgnoreCase(user.getTipoUsuario())) {

            // LÓGICA PARA PROFESSOR: Precisa validar a senha
            if (body.senha() == null || !passwordEncoder.matches(body.senha(), user.getSenha())) {
                // Retorna 401 Unauthorized se a senha estiver errada
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida para professor.");
            }
            // Se a senha estiver correta, gera o token
            token = this.tokenService.generateToken(user);

        } else if ("ALUNO".equalsIgnoreCase(user.getTipoUsuario())) {

            // LÓGICA PARA ALUNO: Não valida senha, só a existência do username
            token = this.tokenService.generateToken(user);

        } else {
            // Tipo de usuário desconhecido
            return ResponseEntity.badRequest().body("Tipo de usuário desconhecido.");
        }

        // 3. Retorna o DTO de resposta com o token
        return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
    }

    /**
     * Endpoint de REGISTRO
     * Agora diferencia o registro de ALUNO (senha nula) e PROFESSOR (senha obrigatória).
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        // 1. Verifica se o username já existe
        Optional<Usuarios> user = this.repository.findByUsername(body.username());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().body("Username já cadastrado.");
        }

        Usuarios newUser = new Usuarios();
        newUser.setUsername(body.username());
        newUser.setNome(body.nome());
        newUser.setSobrenome(body.sobrenome());
        newUser.setTurma(body.turma());
        newUser.setTipoUsuario(body.tipoUsuario()); // Usamos o tipo vindo do JSON

        // 2. Verifica o Tipo de Usuário para decidir a lógica de senha
        if ("PROFESSOR".equalsIgnoreCase(body.tipoUsuario())) {

            // LÓGICA PARA PROFESSOR: Senha é obrigatória
            if (body.senha() == null || body.senha().isEmpty()) {
                return ResponseEntity.badRequest().body("Senha é obrigatória para PROFESSOR.");
            }
            newUser.setSenha(passwordEncoder.encode(body.senha())); // Criptografa a senha

        } else if ("ALUNO".equalsIgnoreCase(body.tipoUsuario())) {

            // LÓGICA PARA ALUNO: Senha deve ser nula
            newUser.setSenha(null);

        } else {
            return ResponseEntity.badRequest().body("Tipo de usuário inválido (use ALUNO ou PROFESSOR).");
        }

        // 3. Salva o novo usuário no banco
        this.repository.save(newUser);

        // 4. Gera um token para o novo usuário já sair logado
        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
    }
}