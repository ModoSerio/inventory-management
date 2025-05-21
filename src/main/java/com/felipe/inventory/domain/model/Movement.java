package com.felipe.inventory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    private Long id;
    private Long productId;
    private MovementType type;
    private Integer quantity;
    private LocalDateTime date;
    private String description;
    
    public enum MovementType {
        ENTRADA, SALIDA
    }
}
