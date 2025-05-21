package com.felipe.inventory.domain.service;

public interface InventoryService {
  /*
   * Actualiza el stock de un producto basado en un movimiento
   * @param productId ID del producto
   * @param quantity Cantidad a mover
   * @param isEntry Indica si es una entrada (true) o salida (false)
   * @return el nuevo valor del stock
   * @throws IllegalArgumentException si el stock resultante es negativo o si una salida resulta en un stock negativo
   */
  int updateStock(Long productId, int quantity, boolean isEntry);

  /*
   * Calcula el factor de rotación de un producto basado en sus movimientos históricos
   * @param productId ID del producto
   * @return el factor de rotación
   */
  double calculateRotationFactor(Long productId);
}
