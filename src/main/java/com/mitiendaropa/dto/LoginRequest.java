package com.mitiendaropa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// --- DTO para el Login de Usuario ---
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "El nombre de usuario o email no puede estar vacío")
    private String usernameOrEmail; // Puede ser username o email
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
