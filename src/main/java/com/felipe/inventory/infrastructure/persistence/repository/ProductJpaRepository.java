package com.felipe.inventory.infrastructure.persistence.repository;

import com.felipe.inventory.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    
    /**
     * Busca productos por nombre, categoría o código usando LIKE
     * @param term término de búsqueda
     * @return lista de productos que coinciden
     */
    @Query("SELECT p FROM ProductEntity p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(p.code) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<ProductEntity> findByTerm(@Param("term") String term);
    
    /**
     * Verifica si existe un producto con el código dado
     * @param code código del producto
     * @return true si existe, false si no
     */
    boolean existsByCode(String code);
}