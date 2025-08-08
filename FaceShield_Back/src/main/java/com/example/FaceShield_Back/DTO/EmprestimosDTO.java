package com.example.FaceShield_Back.DTO;

import com.example.FaceShield_Back.Entity.Emprestimos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimosDTO {

    private Long id;
    private Date data_retirada;
    private Date data_devolucao;
    private String observacoes;

    // Usando DTOs em vez das entidades diretamente
    private UsuariosDTO usuario;
    private FerramentasDTO ferramenta;

    // Conversão de Entidade para DTO
    public static EmprestimosDTO toDTO(Emprestimos entidade) {
        if (entidade == null) {
            return null;
        }

        FerramentasDTO ferramentaDTO = new FerramentasDTO().fromFerramentas(entidade.getFerramenta());
        UsuariosDTO usuarioDTO = UsuariosDTO.toDTO(entidade.getUsuario());

        return new EmprestimosDTO(
                entidade.getId(),
                entidade.getData_retirada(),
                entidade.getData_devolucao(),
                entidade.getObservacoes(),
                usuarioDTO,
                ferramentaDTO
        );
    }

    // Conversão de DTO para Entidade
    public static Emprestimos toEntity(EmprestimosDTO dto) {
        if (dto == null) {
            return null;
        }

        Emprestimos entidade = new Emprestimos();
        entidade.setId(dto.getId());
        entidade.setData_retirada(dto.getData_retirada());
        entidade.setData_devolucao(dto.getData_devolucao());
        entidade.setObservacoes(dto.getObservacoes());

        if (dto.getUsuario() != null) {
            entidade.setUsuario(UsuariosDTO.toEntity(dto.getUsuario()));
        }

        if (dto.getFerramenta() != null) {
            entidade.setFerramenta(dto.getFerramenta().toFerramentas());
        }

        return entidade;
    }
}
