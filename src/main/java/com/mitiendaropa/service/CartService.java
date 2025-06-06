package com.mitiendaropa.service;

import java.math.BigDecimal;

import com.mitiendaropa.dto.CartResponseDTO;


public interface CartService {
    CartResponseDTO getCartForCurrentUserAsDTO(); 
    CartResponseDTO addProductToCart(Long productId, int quantity);
    CartResponseDTO updateProductQuantity(Long productId, int quantity);
    void removeProductFromCart(Long productId);
    void clearCart();
    BigDecimal checkoutCart(); 
}