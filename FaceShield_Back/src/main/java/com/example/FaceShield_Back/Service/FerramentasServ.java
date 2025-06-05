package com.example.FaceShield_Back.Service;

import com.example.FaceShield_Back.DTO.FerramentasDTO;
import com.example.FaceShield_Back.Entity.Ferramentas;
import com.example.FaceShield_Back.Repository.FerramentasRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FerramentasServ {

    @Autowired
    private FerramentasRepo repository;

    // Buscar todos
    public List<Ferramentas> getAllFerramentas() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<FerramentasDTO> getByID(Long id) {
        Optional<Ferramentas> optional = repository.findById(id);

        if (optional.isPresent()) {
            FerramentasDTO dto = new FerramentasDTO();
            return Optional.of(dto.fromFerramentas(optional.get()));
        } else {
            return Optional.empty();
        }
    }
}
