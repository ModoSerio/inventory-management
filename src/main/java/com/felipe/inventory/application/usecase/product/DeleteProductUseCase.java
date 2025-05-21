package com.felipe.inventory.application.usecase.product;

public interface DeleteProductUseCase {
    /**
     * Elimina un producto por su ID
     * @param id El ID del producto a eliminar
     * @throws IllegalArgumentException si el producto no existe
     */
    void execute(Long id);
}