package com.felipe.inventory.application.usecase.movement;

import com.felipe.inventory.domain.model.Movement;
import com.felipe.inventory.domain.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProductMovementsUseCaseImpl implements GetProductMovementsUseCase {
    
    private final MovementRepository movementRepository;
    
    @Override
    public List<Movement> execute(Long productId) {
        log.debug("Obteniendo movimientos para producto ID: {}", productId);
        
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser válido");
        }
        
        List<Movement> movements = movementRepository.findByProductId(productId);
        log.debug("Encontrados {} movimientos para producto ID: {}", movements.size(), productId);
        
        return movements;
    }
}