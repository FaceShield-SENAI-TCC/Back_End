package com.example.FaceShield_Back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ferramentas {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Definindo como NOT NULL
    private String nome;

    @Column(nullable = false) // Definindo como NOT NULL
    private String marca;

    @Column(nullable = false) // Definindo como NOT NULL
    private String modelo;

    @Column(nullable = false) // Definindo como NOT NULL
    private int quantidade;

    @Column(nullable = false) // Definindo como NOT NULL
    private boolean disponibilidade;

    private String descricao;
}
