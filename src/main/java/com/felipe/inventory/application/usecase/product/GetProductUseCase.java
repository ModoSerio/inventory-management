package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;
import java.util.Optional;

public interface GetProductUseCase {
  /**
   * Obtiene un producto por su ID
   * @param id el ID del producto a obtener
   * @return un Optional que contiene el producto si se encuentra, o vacío si no se encuentra
   */
  Optional<Product> execute(Long id);
}
