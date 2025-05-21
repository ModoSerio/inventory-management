package com.felipe.inventory.application.usecase.movement;

import com.felipe.inventory.domain.model.Movement;

public interface RegisterMovementUseCase {
    /**
     * Registra un nuevo movimiento y actualiza el stock del producto
     * @param movement El movimiento a registrar
     * @return El movimiento registrado con su ID asignado
     * @throws IllegalArgumentException si el producto no existe o si la cantidad es inválida
     */
    Movement execute(Movement movement);
}