package com.mitiendaropa.mapper;

import com.mitiendaropa.dto.*;
import com.mitiendaropa.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CartMapper {

     public CartResponseDTO toDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .cartItems(cart.getCartItems().stream() 
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .totalAmount(totalAmount)
                .build();
    }

    public CartItemResponseDTO toDto(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .product(toDto(cartItem.getProduct())) // Llama al mapeo de Product
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .subtotal(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .build();
    }

    public ProductResponseDTO toDto(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .color(product.getColor())
                .size(product.getSize())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(toDto(product.getCategory())) 
                .build();
    }


    public CategoryDTO toDto(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}