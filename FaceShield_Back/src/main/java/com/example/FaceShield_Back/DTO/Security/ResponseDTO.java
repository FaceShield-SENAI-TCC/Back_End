package com.example.FaceShield_Back.DTO.Security;

public record ResponseDTO (Long id, String username, String message) {
    // Agora temos:
    // - id: Long
    // - username: String
    // - message: String (em vez de token)
}