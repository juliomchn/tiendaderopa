package com.mitiendaropa.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"cartItems", "user"})
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
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading es bueno para evitar cargas innecesarias
    @JoinColumn(name = "user_id", nullable = false, unique = true) 
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CartItem> cartItems = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}