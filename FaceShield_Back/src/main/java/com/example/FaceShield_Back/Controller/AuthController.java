package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.Security.RegisterRequestDTO;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Repository.UsuariosRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuariosRepo repository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuariosRepo repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(409).build();
    }
}
