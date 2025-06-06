package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.FerramentasDTO;
import com.example.FaceShield_Back.Entity.Ferramentas;
import com.example.FaceShield_Back.Service.FerramentasServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ferramentas")
public class FerramentasController {

    private final FerramentasServ ferramentasServ;

    @Autowired
    public FerramentasController(FerramentasServ ferramentasServ) {
        this.ferramentasServ = ferramentasServ;
    }

    // Buscar todas as ferramentas com filtros opcionais
    @GetMapping("/buscar")
    public ResponseEntity<List<Ferramentas>> getAllFerramentas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) Long idLocal,
            @RequestParam(required = false) Long idEstado) {

        // Filtro por nome
        if (nome != null && !nome.isEmpty()) {
            List<Ferramentas> ferramentas = ferramentasServ.getAllByNome(nome);
            return ResponseEntity.ok(ferramentas);
        }

        // Filtro por disponibilidade
        if (disponivel != null && disponivel) {
            List<Ferramentas> ferramentas = ferramentasServ.getFerramentasDisponiveis();
            return ResponseEntity.ok(ferramentas);
        }

        // Filtro por local
        if (idLocal != null) {
            List<Ferramentas> ferramentas = ferramentasServ.getFerramentasByLocal(idLocal);
            return ResponseEntity.ok(ferramentas);
        }

        // Filtro por estado
        if (idEstado != null) {
            List<Ferramentas> ferramentas = ferramentasServ.getFerramentasByEstado(idEstado);
            return ResponseEntity.ok(ferramentas);
        }

        // Caso nenhum filtro seja especificado, retorna todas
        List<Ferramentas> todasFerramentas = ferramentasServ.getAllFerramentas();
        return ResponseEntity.ok(todasFerramentas);
    }

    // Buscar ferramenta por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<FerramentasDTO> getFerramentaById(@PathVariable Long id) {
        Optional<FerramentasDTO> ferramenta = ferramentasServ.getByID(id);
        return ferramenta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar nova ferramenta
    @PostMapping("/novaFerramenta")
    public ResponseEntity<FerramentasDTO> createFerramenta(@RequestBody FerramentasDTO ferramentasDTO) {
        FerramentasDTO novaFerramenta = ferramentasServ.createFerramenta(ferramentasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFerramenta);
    }

    // Atualizar ferramenta existente
    @PutMapping("/editar/{idFerramenta}")
    public ResponseEntity<FerramentasDTO> updateFerramenta(
            @PathVariable Long idFerramenta,
            @RequestBody FerramentasDTO ferramentasDTO) {

        Optional<FerramentasDTO> ferramentaAtualizada = ferramentasServ.updateFerramenta(idFerramenta, ferramentasDTO);
        return ferramentaAtualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar ferramenta com mensagem de retorno
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteFerramenta(@PathVariable Long id) {
        boolean deletado = ferramentasServ.deleteFerramenta(id);
        if (deletado) {
            return ResponseEntity.ok("Ferramenta removida com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao remover ferramenta. Ferramenta não encontrada ou já removida.");
        }
    }
}