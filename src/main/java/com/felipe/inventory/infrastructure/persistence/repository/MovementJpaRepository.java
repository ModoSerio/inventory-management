package com.felipe.inventory.infrastructure.persistence.repository;

import com.felipe.inventory.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovementJpaRepository extends JpaRepository<MovementEntity, Long> {
    
    /**
     * Encuentra todos los movimientos de un producto específico
     * @param productId ID del producto
     * @return lista de movimientos del producto
     */
    List<MovementEntity> findByProductId(Long productId);
    
    /**
     * Encuentra movimientos de salida de un producto para calcular rotación
     * @param productId ID del producto
     * @return lista de movimientos de tipo SALIDA
     */
    @Query("SELECT m FROM MovementEntity m WHERE m.productId = :productId AND m.type = 'SALIDA' ORDER BY m.date")
    List<MovementEntity> findExitMovementsByProductId(@Param("productId") Long productId);
}