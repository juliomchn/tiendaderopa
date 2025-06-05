package com.mitiendaropa.service;

import com.mitiendaropa.model.CartItem;

public interface CartItemService {
    CartItem addCartItem(Long productId, int quantity);
    void removeCartItem(Long cartItemId);
    CartItem updateCartItemQuantity(Long cartItemId, int newQuantity);
}