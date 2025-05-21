package com.mitiendaropa.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// --- DTO para la Respuesta de Usuario (para no exponer la contraseña) ---
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    // No incluir la contraseña aquí
}