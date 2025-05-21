package com.mitiendaropa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id; // Para respuestas
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String name;
}