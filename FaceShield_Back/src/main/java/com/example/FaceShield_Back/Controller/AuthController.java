package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.Security.LoginRequestDTO;
import com.example.FaceShield_Back.DTO.Security.RegisterRequestDTO;
import com.example.FaceShield_Back.DTO.Security.ResponseDTO;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Repository.UsuariosRepo;
import com.example.FaceShield_Back.Service.Security.TokenService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginRequestDTO body) {
        Usuarios user = this.repository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (passwordEncoder.matches(body.senha(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);

            return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody RegisterRequestDTO body) {
        Optional<Usuarios> user = this.repository.findByUsername(body.username());

        if (user.isEmpty()) {
            Usuarios newUser = new Usuarios();

            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setUsername(body.username());
            newUser.setNome(body.nome());
            newUser.setSobrenome(body.sobrenome());
            newUser.setTurma(body.turma());
            newUser.setTipoUsuario("PROFESSOR");

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);

            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
