package com.felipe.inventory.infrastructure.adapter;

import com.felipe.inventory.domain.model.Product;
import com.felipe.inventory.domain.repository.ProductRepository;
import com.felipe.inventory.infrastructure.persistence.entity.ProductEntity;
import com.felipe.inventory.infrastructure.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    
    private final ProductJpaRepository productJpaRepository;
    
    @Override
    public Product save(Product product) {
        ProductEntity entity = mapToEntity(product);
        ProductEntity savedEntity = productJpaRepository.save(entity);
        return mapToDomain(savedEntity);
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id)
                .map(this::mapToDomain);
    }
    
    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findByNameOrCategoryOrCode(String term) {
        return productJpaRepository.findByTerm(term).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return productJpaRepository.existsById(id);
    }
    
    // Métodos de mapeo entre dominio e infraestructura
    private ProductEntity mapToEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .code(product.getCode())
                .creationDate(product.getCreationDate())
                .rotationFactor(product.getRotationFactor())
                .build();
    }
    
    private Product mapToDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .category(entity.getCategory())
                .code(entity.getCode())
                .creationDate(entity.getCreationDate())
                .rotationFactor(entity.getRotationFactor())
                .build();
    }
}
/*
 *1. El caso de uso llama al repositorio del dominio
Product product = productRepository.save(newProduct);
2. El adaptador convierte el modelo de dominio a entidad JPA
ProductEntity entity = mapToEntity(newProduct);
3. JPA guarda en la base de datos
ProductEntity savedEntity = productJpaRepository.save(entity);
4. El adaptador convierte de vuelta a modelo de dominio
return mapToDomain(savedEntity);
 */