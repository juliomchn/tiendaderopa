package com.mitiendaropa.model;


import jakarta.persistence.*;
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
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price; // Usar BigDecimal para precios por precisión

    @Column(nullable = false)
    private Integer stock;

    // Relación ManyToOne con Category
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa
    @JoinColumn(name = "category_id", nullable = false) // Columna de unión en la tabla products
    private Category category;

    // private String imageUrl;
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    // Relación OneToMany con OrderItem (un producto puede estar en muchos ítems de pedido)
    // MappedBy indica la propiedad en la entidad OrderItem que posee la relación.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}