package com.example.FaceShield_Back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locais_Ferramentas {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Definindo como NOT NULL
    private String nome_espaco;

    @Column(nullable = false) // Definindo como NOT NULL
    private String armario;

    @Column(nullable = false) // Definindo como NOT NULL
    private String prateleira;

    private String estojo;
}
