package com.example.FaceShield_Back.Controller;

import com.example.FaceShield_Back.DTO.EmprestimosDTO;
import com.example.FaceShield_Back.Entity.Emprestimos;
import com.example.FaceShield_Back.Service.EmprestimosServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimosController {

    private final EmprestimosServ emprestimosServ;

    @Autowired
    public EmprestimosController(EmprestimosServ emprestimosServ) {
        this.emprestimosServ = emprestimosServ;
    }

    // Buscar empréstimos com filtros opcionais
    @GetMapping("/buscar")
    public ResponseEntity<List<Emprestimos>> getAllEmprestimos(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long ferramentaId) {

        if (usuarioId != null) {
            List<Emprestimos> emprestimosPorUsuario = emprestimosServ.getByUsuarioId(usuarioId);
            return ResponseEntity.ok(emprestimosPorUsuario);
        }

        if (ferramentaId != null) {
            List<Emprestimos> emprestimosPorFerramenta = emprestimosServ.getByFerramentaId(ferramentaId);
            return ResponseEntity.ok(emprestimosPorFerramenta);
        }

        List<Emprestimos> todosEmprestimos = emprestimosServ.getAllEmprestimos();
        return ResponseEntity.ok(todosEmprestimos);
    }

    // Buscar empréstimo por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EmprestimosDTO> getEmprestimoById(@PathVariable Long id) {
        Optional<EmprestimosDTO> emprestimo = emprestimosServ.getByID(id);
        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(emprestimo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar novo empréstimo
    @PostMapping("/novoEmprestimo")
    public ResponseEntity<EmprestimosDTO> createEmprestimo(@RequestBody EmprestimosDTO emprestimosDTO) {
        EmprestimosDTO novoEmprestimo = emprestimosServ.createEmprestimo(emprestimosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    // Atualizar empréstimo existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<EmprestimosDTO> updateEmprestimo(
            @PathVariable Long id,
            @RequestBody EmprestimosDTO emprestimosDTO) {

        Optional<EmprestimosDTO> emprestimoAtualizado = emprestimosServ.updateEmprestimo(id, emprestimosDTO);
        if (emprestimoAtualizado.isPresent()) {
            return ResponseEntity.ok(emprestimoAtualizado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Finalizar empréstimo (marcar como devolvido)
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<EmprestimosDTO> finalizarEmprestimo(
            @PathVariable Long id,
            @RequestParam Date dataDevolucao,
            @RequestParam(required = false) String observacoes) {

        Optional<EmprestimosDTO> emprestimoFinalizado = emprestimosServ.finalizarEmprestimo(id, dataDevolucao, observacoes);
        if (emprestimoFinalizado.isPresent()) {
            return ResponseEntity.ok(emprestimoFinalizado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar empréstimo
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteEmprestimo(@PathVariable Long id) {
        boolean deletado = emprestimosServ.deleteEmprestimo(id);
        if (deletado) {
            return ResponseEntity.ok("Empréstimo removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao remover empréstimo. Empréstimo não encontrado ou já removido.");
        }
    }
}