package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;
import com.felipe.inventory.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    
    private final ProductRepository productRepository;
    
    @Override
    public Product execute(Product product) {
        log.info("Creando nuevo producto: {}", product.getName());
        
        // Validaciones de negocio
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del producto es obligatorio");
        }
        
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        
        // Verificar que el código no exista ya
        if (productRepository.findByNameOrCategoryOrCode(product.getCode()).stream()
                .anyMatch(existingProduct -> existingProduct.getCode().equals(product.getCode()))) {
            throw new IllegalArgumentException("Ya existe un producto con el código: " + product.getCode());
        }
        
        // Establecer valores por defecto
        product.setCreationDate(LocalDateTime.now());
        product.setRotationFactor(0.0);
        
        // Si no se especifica stock, inicializar en 0
        if (product.getStock() == null) {
            product.setStock(0);
        }
        
        Product savedProduct = productRepository.save(product);
        log.info("Producto creado exitosamente con ID: {}", savedProduct.getId());
        
        return savedProduct;
    }
}