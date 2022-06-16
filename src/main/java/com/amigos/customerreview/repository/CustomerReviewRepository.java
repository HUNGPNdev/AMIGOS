package com.amigos.customerreview.repository;

import com.amigos.customerreview.model.CustomerReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerReviewRepository extends JpaRepository<com.amigos.customerreview.model.CustomerReviewEntity, UUID> {
//    @Query(value = "select c.id, c.name, c.is_deleted from category c where c.is_deleted = ?1 order by c.id asc limit ?2", nativeQuery = true)
//    List<com.amigos.customerreview.model.CategoryEntity> getLimit(Boolean isDelete, int limit);
//
   @Query("select c from CustomerReviewEntity c where c.productId.id = ?1")
   List<CustomerReviewEntity> getCustomerReviewByCondition(UUID productId);
}
