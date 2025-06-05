package com.example.FaceShield_Back.DTO;

import com.example.FaceShield_Back.Entity.EstadosFerramentas;
import com.example.FaceShield_Back.Entity.Ferramentas;
import com.example.FaceShield_Back.Entity.LocaisFerramentas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FerramentasDTO {

    // Atributos básicos
    private Long id;
    private String nome;
    private String marca;
    private String modelo;
    private int quantidade;
    private boolean disponibilidade;
    private String descricao;

    // Somente os IDs dos relacionamentos
    private Long id_estado;
    private Long id_local;

    // Conversão para entidade
    public Ferramentas toFerramentas() {
        EstadosFerramentas estado = new EstadosFerramentas();
        estado.setId(this.id_estado);

        LocaisFerramentas local = new LocaisFerramentas();
        local.setId(this.id_local);

        return new Ferramentas(
                this.id,
                this.nome,
                this.marca,
                this.modelo,
                this.quantidade,
                this.disponibilidade,
                this.descricao,
                estado,
                local
        );
    }

    // Conversão de entidade para DTO
    public FerramentasDTO fromFerramentas(Ferramentas entidade) {
        return new FerramentasDTO(
                entidade.getId(),
                entidade.getNome(),
                entidade.getMarca(),
                entidade.getModelo(),
                entidade.getQuantidade(),
                entidade.isDisponibilidade(),
                entidade.getDescricao(),
                entidade.getEstado() != null ? entidade.getEstado().getId() : null,
                entidade.getLocal() != null ? entidade.getLocal().getId() : null
        );
    }
}
