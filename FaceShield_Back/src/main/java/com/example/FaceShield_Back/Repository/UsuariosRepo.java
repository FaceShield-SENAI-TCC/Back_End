package com.example.FaceShield_Back.Repository;

import com.example.FaceShield_Back.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuariosRepo extends JpaRepository<Usuarios, Long> {
    // Metodo para buscar pelo nome
    List<Usuarios> findAllByNome(String nome);

    // Metodo para buscar por turma
    List<Usuarios> findAllByTurma(String turma);

    // Buscar por tipo de usuário
    List<Usuarios> findAllByTipoUsuario(String tipoUsuario);
}
