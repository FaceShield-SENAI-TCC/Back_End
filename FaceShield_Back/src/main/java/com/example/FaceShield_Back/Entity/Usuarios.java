package com.example.FaceShield_Back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150) // Definindo como NOT NULL
    private String nome;

    @Column(nullable = false, length = 150) // Definindo como NOT NULL
    private String sobrenome;

    @Column(nullable = true, length = 50)
    private String turma;

    @Column(unique = true, nullable = false, length = 100) // Definindo como NOT NULL e Único
    private String username;

    @Column(nullable = false, length = 100) // Definindo como NOT NULL
    private String senha;

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario", nullable = false)
    private TiposUsuarios tiposUsuario;
}
