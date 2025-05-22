package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;
import com.felipe.inventory.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchProductsUseCaseImpl implements SearchProductsUseCase {
    
    private final ProductRepository productRepository;
    
    @Override
    public List<Product> execute(String term) {
        log.debug("Buscando productos con término: {}", term);
        
        if (term == null || term.trim().isEmpty()) {
            log.debug("Término de búsqueda vacío, retornando todos los productos");
            return productRepository.findAll();
        }
        
        List<Product> products = productRepository.findByNameOrCategoryOrCode(term.trim());
        log.debug("Encontrados {} productos para el término: {}", products.size(), term);
        
        return products;
    }
}