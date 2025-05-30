package com.example.FaceShield_Back.Service;

import com.example.FaceShield_Back.DTO.EstadosFerramentasDTO;
import com.example.FaceShield_Back.Entity.EstadosFerramentas;
import com.example.FaceShield_Back.Repository.EstadosFerramentasRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadosFerramentasServ {

    @Autowired
    private EstadosFerramentasRepo repository;

    // Buscar todos
    public List<EstadosFerramentas> getAllEstados() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<EstadosFerramentasDTO> getByID(Long id) {
        Optional<EstadosFerramentas> optional = repository.findById(id);

        if (optional.isPresent()) {
            EstadosFerramentasDTO dto = new EstadosFerramentasDTO();
            return Optional.of(dto.fromEstadosFerramentasDTO(optional.get()));
        } else {
            return Optional.empty();
        }
    }

    // Criando novo estado
    public EstadosFerramentasDTO createEstado(EstadosFerramentasDTO estadoDTO) {
        EstadosFerramentas estadosFerramentas = estadoDTO.toEstadosFerramentas();
        estadosFerramentas = repository.save(estadosFerramentas);

        return estadoDTO.fromEstadosFerramentasDTO(estadosFerramentas);
    }

    // Atualizando Estado
    public Optional<EstadosFerramentasDTO> updateEstado(Long idEstado, EstadosFerramentasDTO estadoDTO) {
        Optional<EstadosFerramentas> optional = repository.findById(idEstado);

        if (optional.isPresent()) {
            EstadosFerramentas estados = optional.get();

            estados.setNome_estado(estadoDTO.getNome_estado());
            estados.setDescricao(estadoDTO.getDescricao());

            estados = repository.save(estados);

            return Optional.of(estadoDTO.fromEstadosFerramentasDTO(estados));
        } else {
            return Optional.empty();
        }
    }

    // Remover estado
    public boolean deleteEstado(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }
}
