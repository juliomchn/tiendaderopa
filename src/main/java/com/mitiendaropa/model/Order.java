package com.mitiendaropa.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne con User (un pedido pertenece a un usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp // Se genera automáticamente la fecha y hora de creación
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private BigDecimal totalAmount; // El total del pedido

    @Column(nullable = false)
    private String status; // Ej: "PENDING", "COMPLETED", "CANCELLED", "SHIPPED"

    // Relación OneToMany con OrderItem (un pedido contiene muchos ítems de pedido)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    // Campos de dirección de envío, si no usas una entidad Address separada
    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;
    
    @Column(nullable = false)
    private String country;
}
