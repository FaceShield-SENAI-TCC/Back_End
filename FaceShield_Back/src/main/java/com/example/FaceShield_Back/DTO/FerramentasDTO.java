package com.example.FaceShield_Back.DTO;

import com.example.FaceShield_Back.Entity.Ferramentas;
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

    // Atributos de Relacionamentos
    private Long id_estado;
    private Long id_local;

    private EstadosFerramentasDTO estado;
    private LocaisFerramentasDTO espaco;
}
