package com.amigos.product.repository;

import com.amigos.dto.ProductDTO;
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

    @Query("select new com.amigos.dto.ProductDTO(c.id, c.name, c.userId.id, c.description, c.provider, c.title, c.guarantee, c.part, c.image_1, c.image_2, c.image_3, c.isDeleted," +
            "c.cateId.id, c.createAt, c.updateAt, c.cateId.name) from ProductEntity c where c.id in ?1")
    List<ProductDTO> findAllByCateIdAndProductId(List<UUID> productIds);
}
