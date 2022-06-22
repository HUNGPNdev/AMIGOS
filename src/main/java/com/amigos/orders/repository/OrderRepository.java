package com.amigos.orders.repository;

import com.amigos.orders.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID>
{
    @Query("select c from OrderEntity c where c.userId.id = ?1 and c.cartProductSizes.size > 0")
    List<OrderEntity> findAllByUserIdAndCartOrderNotNull(UUID userId);

    @Query("select c from OrderEntity c where c.isDeleted = ?1 and c.cartProductSizes.size > 0")
    List<OrderEntity> findAllByIsDeleted(Boolean isDelete);
}
