package com.felipe.inventory.infrastructure.config;

import com.felipe.inventory.domain.event.StockEventPublisher;
import com.felipe.inventory.infrastructure.adapter.LoggingStockEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventConfiguration {
    
    private final StockEventPublisher stockEventPublisher;
    private final LoggingStockEventListener loggingStockEventListener;
    
    @Bean
    public CommandLineRunner configureEventListeners() {
        return args -> {
            // Suscribir automáticamente el listener de logging al iniciar la aplicación
            stockEventPublisher.subscribe(loggingStockEventListener);
            System.out.println("✅ Listener de stock bajo configurado correctamente");
        };
    }
}