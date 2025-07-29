package com.example.FaceShield_Back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ferramentas {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Definindo como NOT NULL
    private String nome;

    @Column(nullable = false, length = 100) // Definindo como NOT NULL
    private String marca;

    @Column(nullable = false, length = 100) // Definindo como NOT NULL
    private String modelo;

    @Column(nullable = false) // Definindo como NOT NULL
    private int quantidade;

    @Column(nullable = false) // Definindo como NOT NULL
    private boolean disponibilidade;

    @Column(length = 255)
    private String descricao;

    // Relacionamentos Local e Estado de Ferramentas
    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadosFerramentas estado;

    @ManyToOne
    @JoinColumn(name = "id_local", nullable = false)
    private LocaisFerramentas local;

    // Emprestimos
    @OneToMany(mappedBy = "ferramenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Emprestimos> emprestimos;

    // Construtor pro DTO de Ferramentas
    public Ferramentas(Long id, String nome, String marca, String modelo, int quantidade, boolean disponibilidade, String descricao, EstadosFerramentas estado, LocaisFerramentas local) {
    }
}
