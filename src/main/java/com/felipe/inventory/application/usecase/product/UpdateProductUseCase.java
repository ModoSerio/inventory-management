package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;

public interface UpdateProductUseCase {
    /**
     * Actualiza un producto existente
     * @param id El ID del producto a actualizar
     * @param product Los nuevos datos del producto
     * @return El producto actualizado
     * @throws IllegalArgumentException si el producto no existe
     */
    Product execute(Long id, Product product);
}