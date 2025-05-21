package com.mitiendaropa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long id; // ID del OrderItem
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice; // Precio del producto en el momento del pedido
    private BigDecimal subtotal; // quantity * unitPrice
}