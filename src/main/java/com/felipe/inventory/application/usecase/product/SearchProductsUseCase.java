package com.felipe.inventory.application.usecase.product;

import com.felipe.inventory.domain.model.Product;
import java.util.List;

public interface SearchProductsUseCase {
  /**
   * Busca productos por nombre, categoría o código
   * @param term el término de búsqueda
   * @return una lista de productos que coinciden con el término
   */
  List<Product> execute(String term);
}
