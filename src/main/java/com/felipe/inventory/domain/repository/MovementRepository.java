package com.felipe.inventory.domain.repository;

import com.felipe.inventory.domain.model.Movement;
import java.util.List;

public interface MovementRepository {
  Movement save(Movement movement);
  List<Movement> findByProductId(Long productId);
  List<Movement> findAll();
}
