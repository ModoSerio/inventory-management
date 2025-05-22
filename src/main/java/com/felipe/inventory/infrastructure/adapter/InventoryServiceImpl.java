package com.felipe.inventory.infrastructure.adapter;

import com.felipe.inventory.domain.event.LowStockEvent;
import com.felipe.inventory.domain.event.StockEventPublisher;
import com.felipe.inventory.domain.model.Movement;
import com.felipe.inventory.domain.model.Product;
import com.felipe.inventory.domain.repository.MovementRepository;
import com.felipe.inventory.domain.repository.ProductRepository;
import com.felipe.inventory.domain.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    
    private final ProductRepository productRepository;
    private final MovementRepository movementRepository;
    private final StockEventPublisher eventPublisher;
    
    private static final int LOW_STOCK_THRESHOLD = 5;
    
    @Override
    public int updateStock(Long productId, int quantity, boolean isEntry) {
        log.debug("Actualizando stock del producto {}: {} unidades, entrada: {}", 
                 productId, quantity, isEntry);
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + productId));
        
        int newStock;
        if (isEntry) {
            newStock = product.getStock() + quantity;
            log.debug("Entrada de stock: {} + {} = {}", product.getStock(), quantity, newStock);
        } else {
            if (product.getStock() < quantity) {
                throw new IllegalArgumentException(
                    String.format("Stock insuficiente. Stock actual: %d, cantidad solicitada: %d", 
                                product.getStock(), quantity));
            }
            newStock = product.getStock() - quantity;
            log.debug("Salida de stock: {} - {} = {}", product.getStock(), quantity, newStock);
        }
        
        product.setStock(newStock);
        productRepository.save(product);
        
        if (newStock <= LOW_STOCK_THRESHOLD) {
            LowStockEvent event = new LowStockEvent(
                product.getId(),
                product.getName(),
                newStock
            );
            eventPublisher.publishLowStockEvent(event);
        }
        
        log.info("Stock actualizado exitosamente para producto {}: nuevo stock = {}", 
                productId, newStock);
        
        return newStock;
    }
    
    @Override
    public double calculateRotationFactor(Long productId) {
        log.debug("Calculando factor de rotación para producto {}", productId);
        
        List<Movement> exitMovements = movementRepository.findByProductId(productId).stream()
                .filter(movement -> movement.getType() == Movement.MovementType.SALIDA)
                .toList();
        
        if (exitMovements.size() < 2) {
            log.debug("Insuficientes movimientos de salida para calcular factor de rotación");
            return 0.0;
        }
        
        int totalSold = exitMovements.stream()
                .mapToInt(Movement::getQuantity)
                .sum();
        
        LocalDateTime firstSale = exitMovements.stream()
                .map(Movement::getDate)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
        
        LocalDateTime lastSale = exitMovements.stream()
                .map(Movement::getDate)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
        
        long daysBetween = Duration.between(firstSale, lastSale).toDays();
        
        if (daysBetween == 0) {
            daysBetween = 1;
        }
        
        double rotationFactor = (double) totalSold / daysBetween;
        
        log.debug("Factor de rotación calculado: {} unidades vendidas en {} días = {} unidades/día", 
                 totalSold, daysBetween, rotationFactor);
        
        return Math.round(rotationFactor * 100.0) / 100.0;
    }
}