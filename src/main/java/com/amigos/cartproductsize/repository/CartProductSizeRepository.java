package com.amigos.cartproductsize.repository;

import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.cartproductsize.model.EnumStatusCart;
import com.amigos.dto.CartProductSizeDTO;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartProductSizeRepository extends JpaRepository<CartProductSizeEntity, UUID>
{
    @Query("select c from CartProductSizeEntity c where c.productSizeId.id = ?1 and c.userId.id = ?2 and c.status = ?3")
    CartProductSizeEntity findCartByProductSizeAndUserId(UUID productSizeId, UUID userId, EnumStatusCart status);

    @Query("select new com.amigos.dto.CartProductSizeDTO(c.id, c.productSizeId.id, c.userId.id, c.count, c.status, c.createAt, c.productSizeId.price , c.productSizeId.productId.id, "
            + "c.productSizeId.productId.name, c.productSizeId.discount, c.productSizeId.productId.image_1, c.productSizeId.sizeId.id, c.productSizeId.sizeId.name)"
            + " from CartProductSizeEntity c where c.userId.id = ?1 and c.status = ?2 and c.orderId is null")
    List<CartProductSizeDTO> findCartByUserIdAndStatus(UUID userId, EnumStatusCart status);

    int countAllByUserId_IdAndStatus(UUID userId, EnumStatusCart status);

    List<CartProductSizeEntity> findAllByUserId_IdAndStatus(UUID userId, EnumStatusCart status);
    boolean existsByUserId_IdAndStatusAndProductSizeId_Id(UUID userId, EnumStatusCart status, UUID productSizeId);
    @Override
    @Query("select c from CartProductSizeEntity c where c.id = ?1")
    Optional<CartProductSizeEntity> findById(UUID uuid);

    @Query("select case when sum(c.count) > 0 then sum(c.count) else 0 end from CartProductSizeEntity c where c.productSizeId.productId.id = ?1 and c.status <> 'SHOPPING_CART'")
    long countReportByProductId(UUID uuid);
}
