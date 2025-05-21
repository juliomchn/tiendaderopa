package com.mitiendaropa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    private String description;

    @NotBlank(message = "La marca del producto no puede estar vacía")
    private String brand;

    @NotBlank(message = "El color del producto no puede estar vacío")
    private String color;

    @NotBlank(message = "La talla del producto no puede estar vacía")
    private String size;

    @NotNull(message = "El precio del producto es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private BigDecimal price;

    @NotNull(message = "El stock del producto es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    private String categoryName; // Opcional para respuestas
}
