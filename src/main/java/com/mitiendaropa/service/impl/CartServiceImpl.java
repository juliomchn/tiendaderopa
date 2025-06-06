package com.mitiendaropa.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import org.springframework.stereotype.Service;

import com.mitiendaropa.dto.CartResponseDTO;
import com.mitiendaropa.mapper.CartMapper;
import com.mitiendaropa.model.*;
import com.mitiendaropa.repository.CartRepository;
import com.mitiendaropa.repository.ProductRepository;
import com.mitiendaropa.service.CartService;

import jakarta.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserServiceImpl userService;
    private final CartMapper cartMapper;
    private final EntityManager entityManager;

    @Transactional
    public Cart getCartForCurrentUserEntity() {
        User user = userService.getCurrentUser();
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = Cart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build(); // Breakpoint 1: justo antes de guardar

            Cart savedCart = cartRepository.save(newCart); // Breakpoint 2: después de guardar
            entityManager.flush(); // Breakpoint 3: después de hacer flush

            // Breakpoint 4: en la línea que da el error
            return cartRepository.findById(savedCart.getId())
                                .orElseThrow(() -> new IllegalStateException("El carrito recién creado no pudo ser encontrado."));
        });
    }
    @Override
    @Transactional 
    public CartResponseDTO addProductToCart(Long productId, int quantity) {
        Cart cart = getCartForCurrentUserEntity(); 

        // Ya sabemos que trae el carrito existente (o crea uno)
        if (cart.getCartItems() == null) {
            cart.setCartItems(new HashSet<>());
        }
        // Busca el producto
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        boolean itemFound = false;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            CartItem newItem = CartItem.builder()
                    .cart(cart) 
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(product.getPrice()) 
                    .build();
            

            cart.getCartItems().add(newItem);

        }

        cart.setUpdatedAt(LocalDateTime.now());
        
        Cart savedCart = cartRepository.save(cart);

        entityManager.flush(); 
        return cartMapper.toDto(savedCart);
    }

    @Override
    @Transactional
    public CartResponseDTO updateProductQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            removeProductFromCart(productId);
            entityManager.flush();
            return getCartForCurrentUserAsDTO();
        }

        Cart cart = getCartForCurrentUserEntity();

        cart.getCartItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
            }
        });
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        entityManager.flush();
        return cartMapper.toDto(savedCart);
    }

    @Override
    @Transactional
    public void removeProductFromCart(Long productId) {
        Cart cart = getCartForCurrentUserEntity();

        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void clearCart() {
        Cart cart = getCartForCurrentUserEntity();
        cart.getCartItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        entityManager.flush();
    }

    @Override
    @Transactional
    public BigDecimal checkoutCart() {
        Cart cart = getCartForCurrentUserEntity();
        
        BigDecimal total = cart.getCartItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        clearCart(); 

        return total;
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartForCurrentUserAsDTO() {
        Cart cart = getCartForCurrentUserEntity();
        
        return cartMapper.toDto(cart);
    }
}