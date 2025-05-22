package com.felipe.inventory.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id //Key primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Genera el ID automáticamente, IDENTITY es para autoincremental
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description; 
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer stock;
    
    private String category;
    
    @Column(unique = true, nullable = false)
    private String code;
    
    private LocalDateTime creationDate;
    
    private Double rotationFactor;
}