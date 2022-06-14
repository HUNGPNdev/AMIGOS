package com.amigos.cartproductsize.repository;

import com.amigos.cartproductsize.model.CartProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartProductSizeRepository extends JpaRepository<CartProductSizeEntity, UUID>
{

}
