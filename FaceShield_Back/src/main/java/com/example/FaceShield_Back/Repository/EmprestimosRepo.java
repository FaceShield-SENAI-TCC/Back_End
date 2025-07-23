package com.example.FaceShield_Back.Repository;

import com.example.FaceShield_Back.Entity.Emprestimos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimosRepo extends JpaRepository<Emprestimos, Long> {
    // Método para listar empréstimos de um usuário
    // O Spring Data JPA automaticamente entende "findByUsuarioId"
    // porque 'usuario' é um objeto na sua entidade Emprestimos e 'id' é seu ID.
    List<Emprestimos> findByUsuarioId(Long usuarioId);

    // Método para listar empréstimos de uma ferramenta
    // Similarmente, o Spring Data JPA entende "findByFerramentaId"
    List<Emprestimos> findByFerramentaId(Long ferramentaId);
}
