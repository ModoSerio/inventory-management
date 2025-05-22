package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;
import com.felipe.inventory.domain.repository.ProductRepository;
import com.felipe.inventory.domain.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    
    @Override
    public Product execute(Long id, Product updatedProduct) {
        log.info("Actualizando producto con ID: {}", id);
        
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser válido");
        }
        
        // Buscar el producto existente
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        
        // Validaciones
        if (updatedProduct.getName() != null && !updatedProduct.getName().trim().isEmpty()) {
            existingProduct.setName(updatedProduct.getName());
        }
        
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        
        if (updatedProduct.getPrice() != null && updatedProduct.getPrice() > 0) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        
        if (updatedProduct.getCategory() != null) {
            existingProduct.setCategory(updatedProduct.getCategory());
        }
        
        // Verificar código único si se está cambiando
        if (updatedProduct.getCode() != null && !updatedProduct.getCode().equals(existingProduct.getCode())) {
            if (productRepository.findByNameOrCategoryOrCode(updatedProduct.getCode()).stream()
                    .anyMatch(product -> product.getCode().equals(updatedProduct.getCode()) && !product.getId().equals(id))) {
                throw new IllegalArgumentException("Ya existe un producto con el código: " + updatedProduct.getCode());
            }
            existingProduct.setCode(updatedProduct.getCode());
        }
        
        // Recalcular factor de rotación
        double rotationFactor = inventoryService.calculateRotationFactor(id);
        existingProduct.setRotationFactor(rotationFactor);
        
        Product savedProduct = productRepository.save(existingProduct);
        log.info("Producto actualizado exitosamente: {}", savedProduct.getName());
        
        return savedProduct;
    }
}