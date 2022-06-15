package com.amigos.cartproductsize.repository;

import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.cartproductsize.model.EnumStatusCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartProductSizeRepository extends JpaRepository<CartProductSizeEntity, UUID>
{
    @Query("select c from CartProductSizeEntity c where c.productSizeId.id = ?1 and c.userId.id = ?2 and c.status = ?3")
    CartProductSizeEntity findCartByProductSizeAndUserId(UUID productSizeId, UUID userId, EnumStatusCart status);
}
