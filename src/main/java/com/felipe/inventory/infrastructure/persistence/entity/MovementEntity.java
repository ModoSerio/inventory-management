package com.felipe.inventory.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity //Dice que representa una tabla en la base de datos
@Table(name = "movements") //Nombre de la tabla en la base de datos
@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Enumerated(EnumType.STRING) //Guarda Entrada o Salida como String
    @Column(nullable = false)
    private MovementType type;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(length = 500)
    private String description;
    
    public enum MovementType {
        ENTRADA, SALIDA
    }
}