package com.mitiendaropa.service.impl;

import com.mitiendaropa.model.*;
import com.mitiendaropa.repository.CartItemRepository;
import com.mitiendaropa.repository.CartRepository;
import com.mitiendaropa.repository.ProductRepository;
import com.mitiendaropa.service.CartItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public CartItem addCartItem(Long productId, int quantity) {
        Cart cart = getOrCreateCartForCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verifica si ya existe el CartItem
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }


    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem no encontrado"));
        cartItem.setQuantity(newQuantity);
        return cartItemRepository.save(cartItem);
    }

    private Cart getOrCreateCartForCurrentUser() {
        return cartRepository.findByUserId(userService.getCurrentUser().getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userService.getCurrentUser());
                    return cartRepository.save(newCart);
                });
    }
}
