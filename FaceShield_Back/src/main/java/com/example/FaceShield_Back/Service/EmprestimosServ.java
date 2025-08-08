package com.example.FaceShield_Back.Service;

import com.example.FaceShield_Back.DTO.EmprestimosDTO;
import com.example.FaceShield_Back.Entity.Emprestimos;
import com.example.FaceShield_Back.Repository.EmprestimosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimosServ {

    @Autowired
    private EmprestimosRepo repository;

    // Buscar todos emprestimos (retorana entidades)
    public List<Emprestimos> getAllEmprestimos() {
        return repository.findAll();
    }

    // Buscar por ID (retorna Optional<DTO>)
    public Optional<EmprestimosDTO> getByID(Long id) {
        Optional<Emprestimos> optional = repository.findById(id);

        if (optional.isPresent()) {
            return Optional.of(EmprestimosDTO.toDTO(optional.get()));
        } else {
            return Optional.empty();
        }
    }

    // Buscar empréstimos por ID de usuário (retorna entidades)
    public List<Emprestimos> getByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // Buscar empréstimos por ID de ferramenta (retorna entidades)
    public List<Emprestimos> getByFerramentaId(Long ferramentaId) {
        return repository.findByFerramentaId(ferramentaId);
    }

    // Criar novo empréstimo (recebe DTO, retorna DTO)
    public EmprestimosDTO createEmprestimo(EmprestimosDTO emprestimoDTO) {
        Emprestimos emprestimo = EmprestimosDTO.toEntity(emprestimoDTO);
        emprestimo = repository.save(emprestimo);
        return EmprestimosDTO.toDTO(emprestimo);
    }

    // Atualizar empréstimo existente
    public Optional<EmprestimosDTO> updateEmprestimo(Long id, EmprestimosDTO dto) {
        Optional<Emprestimos> optional = repository.findById(id);
        if (optional.isPresent()) {
            Emprestimos emprestimo = optional.get();

            emprestimo.setData_retirada(dto.getData_retirada());
            emprestimo.setData_devolucao(dto.getData_devolucao());
            emprestimo.setObservacoes(dto.getObservacoes());
            // Não atualizamos usuário e ferramenta pois são relações fixas

            Emprestimos updated = repository.save(emprestimo);
            return Optional.of(EmprestimosDTO.toDTO(updated));
        }
        return Optional.empty();
    }

    // Finalizar empréstimo (marcar como devolvido)
    public Optional<EmprestimosDTO> finalizarEmprestimo(Long id, Date dataDevolucao, String observacoes) {
        Optional<Emprestimos> optional = repository.findById(id);
        if (optional.isPresent()) {
            Emprestimos emprestimo = optional.get();

            emprestimo.setData_devolucao(dataDevolucao);
            if (observacoes != null) {
                emprestimo.setObservacoes(observacoes);
            }

            Emprestimos finalizado = repository.save(emprestimo);
            return Optional.of(EmprestimosDTO.toDTO(finalizado));
        }
        return Optional.empty();
    }

    // Remover empréstimo (retorna booleano indicando sucesso)
    public boolean deleteEmprestimo(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
