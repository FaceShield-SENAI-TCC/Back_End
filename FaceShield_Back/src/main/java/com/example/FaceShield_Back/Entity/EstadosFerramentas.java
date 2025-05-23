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
public class EstadosFerramentas {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Definindo como NOT NULL
    private String nome_estado;

    @Column(length = 255)
    private String descricao;

    // Relacionamento
    @OneToMany(mappedBy = "estado")
    private List<Ferramentas> ferramentas;
}
