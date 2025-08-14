package com.example.FaceShield_Back.DTO;

import com.example.FaceShield_Back.Entity.Emprestimos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimosResponseDTO {
    // Atributos
    private Long id;
    private LocalDateTime data_retirada;
    private LocalDateTime data_devolucao;
    private String observacoes;
    private String nomeUsuario;
    private String nomeFerramenta;

    // Método de conversão
    public static EmprestimosResponseDTO toDTO(Emprestimos emprestimos) {
        return new EmprestimosResponseDTO(
                emprestimos.getId(),
                emprestimos.getData_retirada(),
                emprestimos.getData_devolucao(),
                emprestimos.getObservacoes(),

                // Obtém o nome de usuario e ferramenta
                emprestimos.getUsuario().getNome(),
                emprestimos.getFerramenta().getNome()
        );
    }
}
