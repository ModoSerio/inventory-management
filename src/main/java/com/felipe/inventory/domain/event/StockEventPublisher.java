package com.felipe.inventory.domain.event;

public interface StockEventPublisher {
  /*
   * Interfaz que define el publicador de eventos de stock
   * En este caso, el publicador es el que publica el evento de bajo stock
   * Se encarga de gestionar los oyentes y enviar notificaciones
   */
  void subscribe(StockEventListener listener); // Registra un nuevo oyente para recibir notificaciones
  void unsubscribe(StockEventListener listener); // Elimina un oyente de la lista
  void publishLowStockEvent(LowStockEvent event); // Notifica a todos los oyentes registrados sobre el evento
}
