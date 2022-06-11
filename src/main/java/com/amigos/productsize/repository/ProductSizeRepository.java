package com.amigos.productsize.repository;

import com.amigos.dto.ProductSizeDTO;
import com.amigos.productsize.model.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity, UUID> {
    @Query("select new com.amigos.dto.ProductSizeDTO(p.id, p.productId.id, p.price, p.discount, p.count, p.sizeId.id, p.productId.name, p.productId.description, p.productId.provider, p.productId.title, "
            + "p.productId.guarantee, p.productId.part, p.productId.image_1, p.productId.image_2, p.productId.image_3, p.isDeleted, p.sizeId.name) from ProductSizeEntity p where p.isDeleted = ?1")
    List<ProductSizeDTO>  getAllByStatus(boolean isDeleted);
}
