package com.felipe.inventory.infrastructure.adapter;

import com.felipe.inventory.domain.event.LowStockEvent;
import com.felipe.inventory.domain.event.StockEventListener;
import com.felipe.inventory.domain.event.StockEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class StockEventPublisherImpl implements StockEventPublisher {
    
    private final List<StockEventListener> listeners = new CopyOnWriteArrayList<>();
    
    @Override
    public void subscribe(StockEventListener listener) {
        listeners.add(listener);
        log.debug("Nuevo listener suscrito para eventos de stock bajo: {}", listener.getClass().getSimpleName());
    }
    
    @Override
    public void unsubscribe(StockEventListener listener) {
        listeners.remove(listener);
        log.debug("Listener desuscrito de eventos de stock bajo: {}", listener.getClass().getSimpleName());
    }
    
    @Override
    public void publishLowStockEvent(LowStockEvent event) {
        log.info("🚨 Publicando evento de stock bajo para producto ID: {}, stock actual: {}", 
                event.getProductId(), event.getCurrentStock());
        
        for (StockEventListener listener : listeners) {
            try {
                listener.onLowStock(event);
            } catch (Exception e) {
                log.error("Error al notificar listener {}: {}", 
                         listener.getClass().getSimpleName(), e.getMessage());
            }
        }
    }
}