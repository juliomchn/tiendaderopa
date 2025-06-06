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
public class ProductResponseDTO { 
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;
    private CategoryDTO category; // Contiene toda la info de la categoria
}