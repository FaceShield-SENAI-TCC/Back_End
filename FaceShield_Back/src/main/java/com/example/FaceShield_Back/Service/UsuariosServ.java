package com.example.FaceShield_Back.Service;

import com.example.FaceShield_Back.DTO.TiposUsuariosDTO;
import com.example.FaceShield_Back.DTO.UsuariosDTO;
import com.example.FaceShield_Back.Entity.Usuarios;
import com.example.FaceShield_Back.Repository.UsuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServ {

    @Autowired
    private UsuariosRepo repository;

    // Buscar todos os usuários (retorna entidades)
    public List<Usuarios> getAllUsuarios() {
        return repository.findAll();
    }

    // Buscar por ID (retorna Optional<DTO>)
    public Optional<UsuariosDTO> getByID(Long id) {
        Optional<Usuarios> optional = repository.findById(id);

        if (optional.isPresent()) {
            return Optional.of(UsuariosDTO.toDTO(optional.get()));
        } else {
            return Optional.empty();
        }
    }

    // Buscar pelo NOME do usuário (retorna entidades)
    public List<Usuarios> getAllByNome(String nome) {
        return repository.findAllByNome(nome);
    }

    // Buscar por TURMA (retorna entidades)
    public List<Usuarios> getAllByTurma(String turma) {
        return repository.findAllByTurma(turma);
    }

    // Criar novo usuário (recebe DTO, retorna DTO)
    public UsuariosDTO createUsuario(UsuariosDTO usuariosDTO) {
        Usuarios usuario = UsuariosDTO.toEntity(usuariosDTO);
        usuario = repository.save(usuario);
        return UsuariosDTO.toDTO(usuario);
    }

    // Atualizar usuário existente
    public Optional<UsuariosDTO> updateUsuario(Long idUsuario, UsuariosDTO dto) {
        Optional<Usuarios> optional = repository.findById(idUsuario);

        if (optional.isPresent()) {
            Usuarios usuario = optional.get();

            usuario.setNome(dto.getNome());
            usuario.setSobrenome(dto.getSobrenome());
            usuario.setTurma(dto.getTurma());
            usuario.setUsername(dto.getUsername());
            usuario.setSenha(dto.getSenha());
            usuario.setTiposUsuario(TiposUsuariosDTO.toEntity(dto.getTiposUsuario()));

            usuario = repository.save(usuario);

            return Optional.of(UsuariosDTO.toDTO(usuario));
        } else {
            return Optional.empty();
        }
    }

    // Remover usuário (retorna booleano indicando sucesso)
    public boolean deleteUsuario(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}