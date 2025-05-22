package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    
    private final ProductRepository productRepository;
    
    @Override
    public void execute(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser válido");
        }
        
        // Verificar que el producto existe
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
        }
        
        productRepository.deleteById(id);
        log.info("Producto eliminado exitosamente con ID: {}", id);
    }
}