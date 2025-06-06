package com.mitiendaropa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int quantity;
}