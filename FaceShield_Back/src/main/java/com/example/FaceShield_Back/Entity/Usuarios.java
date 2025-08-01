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
public class Usuarios {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150) // Definindo como NOT NULL
    private String nome;

    @Column(nullable = false, length = 150) // Definindo como NOT NULL
    private String sobrenome;

    @Column(nullable = true, length = 50) // Definido como CAN NULL
    private String turma;

    @Column(unique = true, nullable = true, length = 100) // Definindo como CAN NULL e Único
    private String username;

    @Column(nullable = true, length = 100) // Definindo como CAN NULL
    private String senha;

    // Relacionamentos

    // Tipo Usuario
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario", nullable = false)
    private TiposUsuarios tiposUsuario;

    // Emprestimos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Emprestimos> emprestimos; // Mapeado pelo atributo 'usuario' na classe Emprestimos
}
