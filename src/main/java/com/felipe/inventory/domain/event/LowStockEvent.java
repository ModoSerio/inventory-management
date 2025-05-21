package com.felipe.inventory.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockEvent {
  /*
   * Contiene la información del evento (Qué producto tiene bajo stock)
   */
    private Long productId;
    private String productName;
    private Integer currentStock;
}
