package com.amigos.customerreview.repository;

import com.amigos.customerreview.model.CustomerReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerReviewRepository extends JpaRepository<com.amigos.customerreview.model.CustomerReviewEntity, UUID> {

   @Query("select c from CustomerReviewEntity c where c.productId.id = ?1")
   List<CustomerReviewEntity> getCustomerReviewByCondition(UUID productId);
   @Query("select c from CustomerReviewEntity c where c.isDeleted = false ")
   List<CustomerReviewEntity> getCustomerReview();
}
