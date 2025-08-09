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

    // Buscar pelo NOME da ferramenta
    public List<Ferramentas> getAllByNome(String nome) {
        return repository.findAllByNome(nome);
    }

    // Criando nova Ferramenta
    public FerramentasDTO createFerramenta(FerramentasDTO ferramentasDTO) {
        Ferramentas ferramentas = ferramentasDTO.toFerramentas();
        ferramentas = repository.save(ferramentas);

        return ferramentasDTO.fromFerramentas(ferramentas);
    }

    // Buscar ferramentas disponíveis
    public List<Ferramentas> getFerramentasDisponiveis() {
        return repository.findByDisponibilidadeTrue();
    }

    // Buscar ferramentas por local
    public List<Ferramentas> getFerramentasByLocal(Long idLocal) {
        return repository.findByLocalId(idLocal);
    }

    // Atualizando Ferramenta
    public Optional<FerramentasDTO> updateFerramenta(Long idFerramenta, FerramentasDTO dto) {
        Optional<Ferramentas> optional = repository.findById(idFerramenta);

        if (optional.isPresent()) {
            Ferramentas ferramenta = optional.get();

            ferramenta.setNome(dto.getNome());
            ferramenta.setMarca(dto.getMarca());
            ferramenta.setModelo(dto.getModelo());
            ferramenta.setEstado(dto.getEstado());
            ferramenta.setDisponibilidade(dto.isDisponibilidade());
            ferramenta.setDescricao(dto.getDescricao());
            ferramenta.setLocal(dto.toFerramentas().getLocal());

            ferramenta = repository.save(ferramenta);

            return Optional.of(dto.fromFerramentas(ferramenta));
        } else {
            return Optional.empty();
        }
    }

    // Remover ferramenta
    public boolean deleteFerramenta(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }
}
