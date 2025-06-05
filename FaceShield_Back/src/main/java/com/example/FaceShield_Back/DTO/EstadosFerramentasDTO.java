package com.example.FaceShield_Back.DTO;

import com.example.FaceShield_Back.Entity.EstadosFerramentas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadosFerramentasDTO {
    // Atributos
    private Long id;
    private String nome_estado;
    private String descricao;

    // Tranformação de Objetos
    public EstadosFerramentas toEstadosFerramentas() {
        return new EstadosFerramentas(
                this.id,
                this.nome_estado,
                this.descricao
        );
    }

    public EstadosFerramentasDTO fromEstadosFerramentasDTO(EstadosFerramentas estadosFerramentas) {
        return new EstadosFerramentasDTO(
                estadosFerramentas.getId(),
                estadosFerramentas.getNome_estado(),
                estadosFerramentas.getDescricao()
        );
    }
}
