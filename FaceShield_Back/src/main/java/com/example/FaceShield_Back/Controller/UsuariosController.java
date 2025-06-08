package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.UsuariosDTO;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Service.UsuariosServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosServ usuariosServ;

    @Autowired
    public UsuariosController(UsuariosServ usuariosServ) {
        this.usuariosServ = usuariosServ;
    }

    // Buscar usuários com filtros opcionais
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuarios>> getAllUsuarios(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String turma) {

        if (nome != null && !nome.isEmpty()) {
            List<Usuarios> usuariosPorNome = usuariosServ.getAllByNome(nome);
            return ResponseEntity.ok(usuariosPorNome);
        }

        if (turma != null && !turma.isEmpty()) {
            List<Usuarios> usuariosPorTurma = usuariosServ.getAllByTurma(turma);
            return ResponseEntity.ok(usuariosPorTurma);
        }

        List<Usuarios> todosUsuarios = usuariosServ.getAllUsuarios();
        return ResponseEntity.ok(todosUsuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuariosDTO> getUsuarioById(@PathVariable Long id) {
        Optional<UsuariosDTO> usuario = usuariosServ.getByID(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar novo usuário
    @PostMapping("/novoUsuario")
    public ResponseEntity<UsuariosDTO> createUsuario(@RequestBody UsuariosDTO usuariosDTO) {
        UsuariosDTO novoUsuario = usuariosServ.createUsuario(usuariosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // Atualizar usuário existente
    @PutMapping("/editar/{idUsuario}")
    public ResponseEntity<UsuariosDTO> updateUsuario(
            @PathVariable Long idUsuario,
            @RequestBody UsuariosDTO usuariosDTO) {

        Optional<UsuariosDTO> usuarioAtualizado = usuariosServ.updateUsuario(idUsuario, usuariosDTO);
        return usuarioAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar usuário
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        boolean deletado = usuariosServ.deleteUsuario(id);
        if (deletado) {
            return ResponseEntity.ok("Usuário removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao remover usuário. Usuário não encontrado ou já removido.");
        }
    }
}