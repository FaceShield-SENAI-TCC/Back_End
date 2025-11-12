package com.example.FaceShield_Back.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoQRCodeRequestDTO {
    private String qrcode;
    private Long usuarioId;
    private String observacoes;
}
