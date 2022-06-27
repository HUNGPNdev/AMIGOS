package com.amigos.role.repository;

import com.amigos.dto.RoleDTO;
import com.amigos.role.model.Role;
import com.amigos.role.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    @Query("select new com.amigos.dto.RoleDTO(r.id,r.name) from Role as r")
    List<RoleDTO> getAllUser();
}
