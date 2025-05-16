package com.example.FaceShield_Back.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FerramentasDTO {
    // Atributos
    private Long id;
    private String nome;
    private String marca;
    private String modelo;
    private int quantidade;
    private boolean disponibilidade;
    private String descricao;
}
