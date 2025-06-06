package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.EstadosFerramentasDTO;
import com.example.FaceShield_Back.Entity.EstadosFerramentas;
import com.example.FaceShield_Back.Service.EstadosFerramentasServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadosController {

    private final EstadosFerramentasServ estadosFerramentasServ;

    @Autowired
    public EstadosController(EstadosFerramentasServ estadosFerramentasServ) {
        this.estadosFerramentasServ = estadosFerramentasServ;
    }

    // Buscar todos os estados
    @GetMapping("/buscar")
    public ResponseEntity<List<EstadosFerramentas>> getAllEstados() {
        List<EstadosFerramentas> todosEstados = estadosFerramentasServ.getAllEstados();
        return ResponseEntity.ok(todosEstados);
    }

    // Buscar estado por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EstadosFerramentasDTO> getEstadoById(@PathVariable Long id) {
        Optional<EstadosFerramentasDTO> estado = estadosFerramentasServ.getByID(id);

        return estado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar novo estado
    @PostMapping("/novoEstado")
    public ResponseEntity<EstadosFerramentasDTO> createEstado(@RequestBody EstadosFerramentasDTO estadoDTO) {
        EstadosFerramentasDTO novoEstado = estadosFerramentasServ.createEstado(estadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
    }

    // Atualizar estado existente
    @PutMapping("/editar/{idEstado}")
    public ResponseEntity<EstadosFerramentasDTO> updateEstado(
            @PathVariable Long idEstado,
            @RequestBody EstadosFerramentasDTO estadoDTO) {

        Optional<EstadosFerramentasDTO> estadoAtualizado = estadosFerramentasServ.updateEstado(idEstado, estadoDTO);

        return estadoAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar estado com mensagem de retorno
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteEstado(@PathVariable Long id) {
        boolean deletado = estadosFerramentasServ.deleteEstado(id);

        if (deletado) {
            return ResponseEntity.ok("Estado removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao remover estado. Estado não encontrado ou já removido.");
        }
    }
}