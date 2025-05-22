package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;
import com.felipe.inventory.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProductUseCaseImpl implements GetProductUseCase {
    
    private final ProductRepository productRepository;
    
    @Override
    public Optional<Product> execute(Long id) {
        log.debug("Buscando producto con ID: {}", id);
        
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser válido");
        }
        
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            log.debug("Producto encontrado: {}", product.get().getName());
        } else {
            log.debug("Producto con ID {} no encontrado", id);
        }
        
        return product;
    }
}