package com.example.FaceShield_Back.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadosFerramentasDTO {
    // Atributos
    private Long id;
    private String nome_estado;
    private String descricao;
}
