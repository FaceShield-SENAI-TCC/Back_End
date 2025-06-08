package com.example.FaceShield_Back.Service;

import com.example.FaceShield_Back.DTO.TiposUsuariosDTO;
import com.example.FaceShield_Back.Entity.TiposUsuarios;
import com.example.FaceShield_Back.Repository.TiposUsuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoUsuarioServ {

    @Autowired
    private TiposUsuariosRepo repository;

    // Buscar todos
    public List<TiposUsuarios> getAllTiposUsuarios() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<TiposUsuariosDTO> getById(Long id) {
        Optional<TiposUsuarios> optional = repository.findById(id);

        return optional.map(TiposUsuariosDTO::toDTO);
    }

    // Criar novo tipo de usuário
    public TiposUsuariosDTO createTipoUsuario(TiposUsuariosDTO dto) {
        TiposUsuarios entity = TiposUsuariosDTO.toEntity(dto);
        entity = repository.save(entity);
        return TiposUsuariosDTO.toDTO(entity);
    }

    // Atualizar tipo de usuário
    public Optional<TiposUsuariosDTO> updateTipoUsuario(Long id, TiposUsuariosDTO dto) {
        Optional<TiposUsuarios> optional = repository.findById(id);

        if (optional.isPresent()) {
            TiposUsuarios entity = optional.get();
            entity.setTipoUsuario(dto.getTipoUsuario());

            entity = repository.save(entity);
            return Optional.of(TiposUsuariosDTO.toDTO(entity));
        } else {
            return Optional.empty();
        }
    }

    // Deletar tipo de usuário
    public boolean deleteTipoUsuario(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
