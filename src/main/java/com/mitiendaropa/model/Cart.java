package com.mitiendaropa.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación OneToOne con User (un carrito por usuario)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true) // La columna user_id será única
    private User user;

    // Relación ManyToMany con Product a través de una tabla de unión 'cart_items'
    // Esta es una forma sencilla. Para añadir cantidad, es mejor una entidad intermedia (CartItem)
    @ManyToMany
    @JoinTable(
        name = "cart_products",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    // La forma más robusta es usar una entidad intermedia para los ítems del carrito (CartItem)
    // Esto permite almacenar la cantidad de cada producto en el carrito.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>(); // Inicializar para evitar NullPointerException

    // Campos adicionales del carrito (opcional)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}