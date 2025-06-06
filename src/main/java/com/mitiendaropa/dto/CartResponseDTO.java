package com.mitiendaropa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO { 
    private Long id;
    private Long userId; 
    // private UserResponseDTO user; // O podrías anidar el DTO del usuario si necesitas más detalles
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItemResponseDTO> cartItems; 
    private BigDecimal totalAmount; 
}