package com.amigos.category.repository;

import com.amigos.category.model.CategoryEntity;
import com.amigos.dto.CategoryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    @Query(value = "select c.id, c.name, c.is_deleted from category c where c.is_deleted = ?1 order by c.id asc limit ?2", nativeQuery = true)
    List<CategoryEntity> getLimit(Boolean isDelete, int limit);

//    @Query("select new com.amigos.dto.CategoryDTO(c.id, c.name, c.isDeleted) from CategoryEntity c where c.isDeleted=?1 and c.name like concat('%', ?2, '%') order by c.id asc")
//    List<CategoryDTO> getCateByCondition(Boolean isDelete, String name, Pageable pageable);

    @Query("select new com.amigos.dto.CategoryDTO(c.id, c.name, c.isDeleted) from CategoryEntity c")
    List<CategoryDTO> getCateByCondition();
}
