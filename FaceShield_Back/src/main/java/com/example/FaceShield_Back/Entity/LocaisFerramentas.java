package com.example.FaceShield_Back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocaisFerramentas {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Definindo como NOT NULL
    private String nome_espaco;

    @Column(nullable = false, length = 50) // Definindo como NOT NULL
    private String armario;

    @Column(nullable = false, length = 50) // Definindo como NOT NULL
    private String prateleira;

    @Column(length = 50)
    private String estojo;

    // Relacionamento
    @OneToMany(mappedBy = "local")
    private List<Ferramentas> ferramentas;
}
