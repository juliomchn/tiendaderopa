package com.mitiendaropa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private String username; // Para la respuesta
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String shippingAddress;
    @NotBlank(message = "La ciudad es obligatoria")
    private String city;
    @NotBlank(message = "El código postal es obligatorio")
    private String postalCode;
    @NotBlank(message = "El país es obligatorio")
    private String country;

    private List<OrderItemDTO> items; // Lista de ítems en el pedido
}