package com.example.FaceShield_Back.DTO;

import com.example.FaceShield_Back.Entity.TiposUsuarios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TiposUsuariosDTO {

    // Atributos
    private Long id;
    private TiposUsuarios.TipoUsuario tipoUsuario;

    // Conversão de Entidade para DTO
    public static TiposUsuariosDTO toDTO(TiposUsuarios entidade) {
        return new TiposUsuariosDTO(entidade.getId(), entidade.getTipoUsuario());
    }

    // Conversão de DTO para Entidade
    public static TiposUsuarios toEntity(TiposUsuariosDTO dto) {
        TiposUsuarios entidade = new TiposUsuarios();

        entidade.setId(dto.getId());
        entidade.setTipoUsuario(dto.getTipoUsuario());

        return entidade;
    }
}
