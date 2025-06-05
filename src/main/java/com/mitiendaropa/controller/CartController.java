package com.mitiendaropa.controller;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mitiendaropa.dto.AddToCartRequest;
import com.mitiendaropa.dto.CartResponseDTO;
import com.mitiendaropa.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/")
    public ResponseEntity<CartResponseDTO> getCart() {
        return ResponseEntity.ok(cartService.getCartForCurrentUserAsDTO());
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartResponseDTO updatedCart = cartService.addProductToCart(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponseDTO> updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateProductQuantity(productId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProduct(@RequestParam Long productId) {
        cartService.removeProductFromCart(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<BigDecimal> checkout() {
        BigDecimal total = cartService.checkoutCart();
        return ResponseEntity.ok(total);
    }
}
