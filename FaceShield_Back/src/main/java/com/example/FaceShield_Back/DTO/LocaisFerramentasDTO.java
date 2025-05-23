package com.example.FaceShield_Back.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocaisFerramentasDTO {
    // Atributos
    private Long id;
    private String nome_espaco;
    private String armario;
    private String prateleira;
    private String estojo;
}
