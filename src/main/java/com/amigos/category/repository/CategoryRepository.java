package com.amigos.category.repository;

import com.amigos.category.model.CategoryEntity;
import com.amigos.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    @Query("select new com.amigos.dto.CategoryDTO(c.id, c.name, c.isDeleted) from CategoryEntity c where c.isDeleted = ?1")
    List<CategoryDTO> getAll(Boolean isDelete);
}
