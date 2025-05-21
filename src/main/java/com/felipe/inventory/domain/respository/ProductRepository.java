package com.felipe.inventory.domain.respository;

import com.felipe.inventory.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id); //optional para evitar NullPointerException
    Product save(Product product);
    void deleteById(Long id);
    List<Product> findByNameOrCategoryOrCode(String term); // Evito crear los 3 métodos y uso term
    boolean existsById(Long id);
}
