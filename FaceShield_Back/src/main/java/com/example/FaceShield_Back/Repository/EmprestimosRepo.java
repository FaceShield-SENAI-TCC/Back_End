package com.example.FaceShield_Back.Repository;

import com.example.FaceShield_Back.Entity.Emprestimos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmprestimosRepo extends JpaRepository<Emprestimos, Long> {
    // Metodo para listar empréstimos de um usuário
    // O Spring Data JPA automaticamente entende "findByUsuarioId"
    // porque 'usuario' é um objeto na sua entidade Emprestimos e 'id' é seu ID.
    List<Emprestimos> findByUsuarioId(Long usuarioId);

    // Metodo para listar empréstimos de uma ferramenta
    // Similarmente, o Spring Data JPA entende "findByFerramentaId"
    List<Emprestimos> findByFerramentaId(Long ferramentaId);

    // Metodo para verificar se a ferramenta está emprestada
    @Query("SELECT e FROM Emprestimos e WHERE e.ferramenta.id = :ferramentaId AND e.data_devolucao IS NULL")
    List<Emprestimos> findEmprestimosAtivosByFerramentaId(@Param("ferramentaId") Long ferramentaId);
}
