package com.felipe.inventory.infrastructure.adapter;

import com.felipe.inventory.domain.event.LowStockEvent;
import com.felipe.inventory.domain.event.StockEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingStockEventListener implements StockEventListener {
    
    @Override
    public void onLowStock(LowStockEvent event) {
        log.warn("🚨 ALERTA: Stock bajo detectado!");
        log.warn("   Producto ID: {}", event.getProductId());
        log.warn("   Producto: {}", event.getProductName());
        log.warn("   Stock actual: {} unidades", event.getCurrentStock());
        log.warn("   Se recomienda reabastecer este producto");
    }
}