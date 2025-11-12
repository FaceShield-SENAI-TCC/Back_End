package com.example.FaceShield_Back.Service;

import com.example.FaceShield_Back.DTO.EmprestimosDTO;
import com.example.FaceShield_Back.DTO.FerramentasDTO;
import com.example.FaceShield_Back.DTO.UsuariosDTO;
import com.example.FaceShield_Back.DTO.responses.EmprestimosResponseDTO;
import com.example.FaceShield_Back.Entity.Emprestimos;
import com.example.FaceShield_Back.Entity.Ferramentas;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Repository.EmprestimosRepo;
import com.example.FaceShield_Back.Repository.FerramentasRepo;
import com.example.FaceShield_Back.Repository.UsuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmprestimosServ {

    @Autowired
    private EmprestimosRepo repository;

    // Buscar todos empréstimos (agora retorna List<EmprestimosResponseDTO>)
    public List<EmprestimosResponseDTO> getAllEmprestimos() {
        return repository.findAll().stream()
                .map(EmprestimosResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID (agora retorna Optional<EmprestimosResponseDTO>)
    public Optional<EmprestimosResponseDTO> getByID(Long id) {
        return repository.findById(id)
                .map(EmprestimosResponseDTO::toDTO);
    }

    // Buscar empréstimos por ID de usuário (agora retorna List<EmprestimosResponseDTO>)
    public List<EmprestimosResponseDTO> getByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(EmprestimosResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar empréstimos por ID de ferramenta (agora retorna List<EmprestimosResponseDTO>)
    public List<EmprestimosResponseDTO> getByFerramentaId(Long ferramentaId) {
        return repository.findByFerramentaId(ferramentaId).stream()
                .map(EmprestimosResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    // Criar novo empréstimo (mantém EmprestimosDTO para input)
    public EmprestimosResponseDTO createEmprestimo(EmprestimosDTO emprestimoDTO) {
        Emprestimos emprestimo = EmprestimosDTO.toEntity(emprestimoDTO);
        emprestimo = repository.save(emprestimo);
        return EmprestimosResponseDTO.toDTO(emprestimo);
    }
/*
    // Criar empréstimo por QRCode e ID do usuário
    public EmprestimosResponseDTO createEmprestimoByQRCode(String qrcode, Long usuarioId, String observacoes) {
        // Buscar ferramenta pelo QRCode
        Optional<Ferramentas> ferramentaOpt = FerramentasRepo.findByQrCode(qrcode);
        if (ferramentaOpt.isEmpty()) {
            throw new RuntimeException("Ferramenta não encontrada com o QRCode: " + qrcode);
        }

        // Buscar usuário pelo ID
        Optional<Usuarios> usuarioOpt = UsuariosRepo.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + usuarioId);
        }

        Ferramentas ferramenta = ferramentaOpt.get();
        Usuarios usuario = usuarioOpt.get();

        // Verificar se a ferramenta já está emprestada
        if (isFerramentaEmprestada(ferramenta.getId())) {
            throw new RuntimeException("Ferramenta já está emprestada");
        }

        // Criar o DTO seguindo o mesmo padrão do metodo existente
        EmprestimosDTO emprestimoDTO = new EmprestimosDTO();
        emprestimoDTO.setData_retirada(LocalDateTime.now());
        emprestimoDTO.setData_devolucao(null);
        emprestimoDTO.setObservacoes(observacoes != null ? observacoes : "Empréstimo via QRCode");

        // Converter as entidades para DTOs (JUNTANDO usuário ID e ferramenta QRCode)
        emprestimoDTO.setUsuario(UsuariosDTO.toDTO(usuario));
        emprestimoDTO.setFerramenta(new FerramentasDTO().fromFerramentas(ferramenta));

        // Usar o metodo existente para criar o empréstimo
        return createEmprestimo(emprestimoDTO);
    }

    // Metodo auxiliar para verificar se a ferramenta já está emprestada
    private boolean isFerramentaEmprestada(Long ferramentaId) {
        List<Emprestimos> emprestimosAtivos = repository.findByFerramentaEmprestimo(ferramentaId);
        return !emprestimosAtivos.isEmpty();
    }
 */

    // Atualizar empréstimo existente (mantém EmprestimosDTO para input)
    public Optional<EmprestimosResponseDTO> updateEmprestimo(Long id, EmprestimosDTO dto) {
        return repository.findById(id)
                .map(emprestimo -> {
                    emprestimo.setData_retirada(dto.getData_retirada());
                    emprestimo.setData_devolucao(dto.getData_devolucao());
                    emprestimo.setObservacoes(dto.getObservacoes());
                    // Não atualizamos usuário e ferramenta pois são relações fixas
                    Emprestimos updated = repository.save(emprestimo);
                    return EmprestimosResponseDTO.toDTO(updated);
                });
    }

    // Finalizar empréstimo (marcar como devolvido)
    public Optional<EmprestimosResponseDTO> finalizarEmprestimo(Long id, LocalDateTime dataDevolucao, String observacoes) {
        return repository.findById(id)
                .map(emprestimo -> {
                    emprestimo.setData_devolucao(dataDevolucao);
                    if (observacoes != null) {
                        emprestimo.setObservacoes(observacoes);
                    }
                    Emprestimos finalizado = repository.save(emprestimo);
                    return EmprestimosResponseDTO.toDTO(finalizado);
                });
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