package com.felipe.inventory.domain.event;

public interface StockEventListener {
  /*
   * Interfaz que implmentan los listeners de eventos de stock
   * En este caso, el listener es el que recibe el evento de bajo stock
   */
  void onLowStock(LowStockEvent event);
}