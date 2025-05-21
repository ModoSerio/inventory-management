package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;

public interface CreateProductUseCase {
  /** 
   * Crea un nuevo producto en el sistema
   * @param product el producto a crear
   * @return el producto creado con su ID asignado
   */
  Product execute(Product product);
}