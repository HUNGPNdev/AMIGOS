package com.amigos.blog.repository;

import com.amigos.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
    @Query(value = "select * from blog b where c.is_deleted = ?1 order by c.id desc limit ?2", nativeQuery = true)
    List<Blog> getLimit(Boolean isDelete, int limit);

    @Query("select b from Blog b where b.is_deleted = ?1")
    List<Blog> findAllByIsDeleted(boolean isDeleted);

}
