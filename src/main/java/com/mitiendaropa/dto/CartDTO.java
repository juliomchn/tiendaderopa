package com.mitiendaropa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
private Long id;
private Long userId;
private List<CartDTO> items; // Lista de ítems en el carrito
private BigDecimal totalAmount;
}