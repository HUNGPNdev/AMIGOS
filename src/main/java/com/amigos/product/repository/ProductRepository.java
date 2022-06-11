package com.amigos.product.repository;

import com.amigos.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("select c from ProductEntity c where c.isDeleted = ?1")
    List<ProductEntity> findAllByIsDeleted(boolean isDeleted);

    @Query("select c from ProductEntity c where c.id in ?1")
    List<ProductEntity> findAllByCateIdAndProductId(List<UUID> productIds);
}
