package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.TiposUsuariosDTO;
import com.example.FaceShield_Back.Entity.TiposUsuarios;
import com.example.FaceShield_Back.Service.TipoUsuarioServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tiposUsuarios")
public class TiposUsuariosController {

    private final TipoUsuarioServ tipoUsuarioServ;

    @Autowired
    public TiposUsuariosController(TipoUsuarioServ tipoUsuarioServ) {
        this.tipoUsuarioServ = tipoUsuarioServ;
    }

    // Buscar todos os tipos de usuário
    @GetMapping("/buscar")
    public ResponseEntity<List<TiposUsuarios>> getAllTiposUsuarios() {
        List<TiposUsuarios> lista = tipoUsuarioServ.getAllTiposUsuarios();
        return ResponseEntity.ok(lista);
    }

    // Buscar tipo de usuário por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TiposUsuariosDTO> getTipoUsuarioById(@PathVariable Long id) {
        Optional<TiposUsuariosDTO> tipo = tipoUsuarioServ.getById(id);

        return tipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar novo tipo de usuário
    @PostMapping("/novoTipo")
    public ResponseEntity<TiposUsuariosDTO> createTipoUsuario(@RequestBody TiposUsuariosDTO dto) {
        TiposUsuariosDTO novo = tipoUsuarioServ.createTipoUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // Atualizar tipo de usuário existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<TiposUsuariosDTO> updateTipoUsuario(
            @PathVariable Long id,
            @RequestBody TiposUsuariosDTO dto) {

        Optional<TiposUsuariosDTO> atualizado = tipoUsuarioServ.updateTipoUsuario(id, dto);

        return atualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar tipo de usuário com mensagem de retorno
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteTipoUsuario(@PathVariable Long id) {
        boolean deletado = tipoUsuarioServ.deleteTipoUsuario(id);

        if (deletado) {
            return ResponseEntity.ok("Tipo de usuário removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao remover tipo de usuário. ID não encontrado ou já removido.");
        }
    }
}
