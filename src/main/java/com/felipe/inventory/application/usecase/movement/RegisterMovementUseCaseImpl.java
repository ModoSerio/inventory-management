package com.felipe.inventory.application.usecase.movement;

import com.felipe.inventory.domain.model.Movement;
import com.felipe.inventory.domain.repository.MovementRepository;
import com.felipe.inventory.domain.repository.ProductRepository;
import com.felipe.inventory.domain.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterMovementUseCaseImpl implements RegisterMovementUseCase {
    
    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    
    @Override
    @Transactional  // Asegura que toda la operación sea , si alguna parte falla, toda la operación se revierte
    public Movement execute(Movement movement) {
        log.info("Registrando movimiento {} para producto ID: {}, cantidad: {}", 
                movement.getType(), movement.getProductId(), movement.getQuantity());
        
        // Validaciones
        if (movement.getProductId() == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio");
        }
        
        if (movement.getQuantity() == null || movement.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        
        if (movement.getType() == null) {
            throw new IllegalArgumentException("El tipo de movimiento es obligatorio");
        }
        
        // Verificar que el producto existe
        if (!productRepository.existsById(movement.getProductId())) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + movement.getProductId());
        }
        
        // Establecer fecha si no se proporciona
        if (movement.getDate() == null) {
            movement.setDate(LocalDateTime.now());
        }
        
        // Actualizar el stock del producto usando el servicio de inventario
        boolean isEntry = movement.getType() == Movement.MovementType.ENTRADA;
        inventoryService.updateStock(movement.getProductId(), movement.getQuantity(), isEntry);
        
        // Guardar el movimiento
        Movement savedMovement = movementRepository.save(movement);
        
        log.info("Movimiento registrado exitosamente con ID: {}", savedMovement.getId());
        
        return savedMovement;
    }
}