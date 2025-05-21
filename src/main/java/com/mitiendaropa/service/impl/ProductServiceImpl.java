package com.mitiendaropa.service.impl;


import com.mitiendaropa.dto.ProductDTO;
import com.mitiendaropa.exception.ResourceNotFoundException;
import com.mitiendaropa.model.Category;
import com.mitiendaropa.model.Product;
import com.mitiendaropa.repository.CategoryRepository;
import com.mitiendaropa.repository.ProductRepository;
import com.mitiendaropa.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Buscar la categoría por ID
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productDTO.getCategoryId()));

        // Mapear DTO a entidad
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .brand(productDTO.getBrand())
                .color(productDTO.getColor())
                .size(productDTO.getSize())
                .category(category) // Asignar la entidad Category
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        return mapToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        // Actualizar campos
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setBrand(productDTO.getBrand());
        product.setColor(productDTO.getColor());
        product.setSize(productDTO.getSize());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());


        // Si la categoría cambia
        if (!product.getCategory().getId().equals(productDTO.getCategoryId())) {
            Category newCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productDTO.getCategoryId()));
            product.setCategory(newCategory);
        }

        Product updatedProduct = productRepository.save(product);
        return mapToDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
    }

    // Método auxiliar para mapear entidad a DTO
    private ProductDTO mapToDTO(Product product) {
    return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .brand(product.getBrand())
            .color(product.getColor())
            .size(product.getSize())
            .price(product.getPrice())
            .stock(product.getStock())
            .categoryId(product.getCategory().getId())
            .categoryName(product.getCategory().getName()) // si lo necesitas
            .build();
}
}