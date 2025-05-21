package com.mitiendaropa.repository;


import com.mitiendaropa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    // Puedes añadir métodos de búsqueda personalizados, por ejemplo:
    // List<Product> findByCategoryId(Long categoryId);
    // List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}