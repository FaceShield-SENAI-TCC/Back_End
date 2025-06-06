package com.example.FaceShield_Back.Repository;

import com.example.FaceShield_Back.Entity.Ferramentas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FerramentasRepo extends JpaRepository<Ferramentas, Long> {
    List<Ferramentas> findAllByNome(String nome); // Metodo para buscar pelo nome
}
