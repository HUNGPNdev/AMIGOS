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
            + "p.productId.guarantee, p.productId.part, p.productId.image_1, p.productId.image_2, p.productId.image_3, p.isDeleted, p.sizeId.name, p.productId.cateId.name, p.productId.cateId.id) from ProductSizeEntity p where p.isDeleted = ?1")
    List<ProductSizeDTO>  getAllByStatus(boolean isDeleted);

    @Query("select new com.amigos.dto.ProductSizeDTO(p.id, p.productId.id, p.price, p.discount, p.count, p.sizeId.id, p.productId.name, p.productId.description, " +
            "p.productId.provider, p.productId.title, p.productId.guarantee, p.productId.part, p.productId.image_1, p.productId.image_2, p.productId.image_3, " +
            "p.isDeleted, p.sizeId.name, p.productId.cateId.name, p.productId.cateId.id) from ProductSizeEntity p where p.isDeleted = false and p.productId.cateId.id = ?1")
    List<ProductSizeDTO>  getAllProductSizeByCateId(UUID cateId);

    @Query("select new com.amigos.dto.ProductSizeDTO(p.id, p.productId.id, p.price, p.discount, p.count, p.sizeId.id, p.productId.name, p.productId.description, " +
            "p.productId.provider, p.productId.title, p.productId.guarantee, p.productId.part, p.productId.image_1, p.productId.image_2, p.productId.image_3, " +
            "p.isDeleted, p.sizeId.name, p.productId.cateId.name, p.productId.cateId.id) from ProductSizeEntity p where p.productId.id = ?1 and p.isDeleted = false")
    List<ProductSizeDTO> findProductSizeByProductId(UUID productId);

    @Query("select distinct(p.productId.id) from ProductSizeEntity p where p.productId.cateId.id = ?1 and p.productId.isDeleted = false ")
    List<UUID> getProductIdByCateId(UUID cateId);

    @Query("select new com.amigos.dto.ProductSizeDTO(p.id, p.productId.id, p.price, p.discount, p.count, p.sizeId.id, p.productId.name, p.productId.description, " +
            "p.productId.provider, p.productId.title, p.productId.guarantee, p.productId.part, p.productId.image_1, p.productId.image_2, p.productId.image_3, " +
            "p.isDeleted, p.sizeId.name, p.productId.cateId.name, p.productId.cateId.id) from ProductSizeEntity p where p.productId.id in ?1")
    List<ProductSizeDTO> getProductSizesNewReleases(List<UUID> productIds);
}
