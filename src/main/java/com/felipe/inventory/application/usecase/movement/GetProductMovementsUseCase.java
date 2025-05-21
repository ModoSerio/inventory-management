package com.felipe.inventory.application.usecase.movement;

import com.felipe.inventory.domain.model.Movement;
import java.util.List;

public interface GetProductMovementsUseCase {
    /**
     * Obtiene los movimientos históricos de un producto
     * @param productId El ID del producto
     * @return Lista de movimientos del producto
     */
    List<Movement> execute(Long productId);
}