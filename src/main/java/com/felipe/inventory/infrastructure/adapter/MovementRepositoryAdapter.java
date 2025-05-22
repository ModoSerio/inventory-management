package com.felipe.inventory.infrastructure.adapter;

import com.felipe.inventory.domain.model.Movement;
import com.felipe.inventory.domain.repository.MovementRepository;
import com.felipe.inventory.infrastructure.persistence.entity.MovementEntity;
import com.felipe.inventory.infrastructure.persistence.repository.MovementJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepository {
    
    private final MovementJpaRepository movementJpaRepository;
    
    @Override
    public Movement save(Movement movement) {
        MovementEntity entity = mapToEntity(movement); //este método es el que convierte el movimiento de dominio a entidad
        MovementEntity savedEntity = movementJpaRepository.save(entity);
        return mapToDomain(savedEntity); 
    }
    
    @Override
    public List<Movement> findByProductId(Long productId) {
        return movementJpaRepository.findByProductId(productId).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Movement> findAll() {
        return movementJpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    // Métodos de mapeo
    private MovementEntity mapToEntity(Movement movement) {
        return MovementEntity.builder()
                .id(movement.getId())
                .productId(movement.getProductId())
                .type(MovementEntity.MovementType.valueOf(movement.getType().name()))
                .quantity(movement.getQuantity())
                .date(movement.getDate())
                .description(movement.getDescription())
                .build();
    }
    
    private Movement mapToDomain(MovementEntity entity) {
        return Movement.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .type(Movement.MovementType.valueOf(entity.getType().name()))
                .quantity(entity.getQuantity())
                .date(entity.getDate())
                .description(entity.getDescription())
                .build();
    }
}