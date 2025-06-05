package com.mitiendaropa.repository;


import com.mitiendaropa.model.Cart;
import com.mitiendaropa.model.CartItem;
import com.mitiendaropa.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteByCart(Cart cart);
}