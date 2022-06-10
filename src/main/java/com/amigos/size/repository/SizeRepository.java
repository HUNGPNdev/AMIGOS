package com.amigos.size.repository;

import com.amigos.product.model.ProductEntity;
import com.amigos.size.model.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, UUID> {
}
