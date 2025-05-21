package com.mitiendaropa.dto;

import jakarta.validation.constraints.Min;
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
public class CartItemDTO {
    private Long id; // ID del CartItem
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;
    private String productName; // Para la respuesta
    private String productDescription; // Para la respuesta
    private BigDecimal productPrice; // Precio actual del producto (al momento de obtener el carrito)
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
    private BigDecimal subtotal; // quantity * productPrice
}