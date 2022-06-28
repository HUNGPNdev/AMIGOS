package com.amigos.user.repository;

import com.amigos.dto.UserDTO;
import com.amigos.dto.UserInputDto;
import com.amigos.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
    @Query("select new com.amigos.dto.UserInputDto(u.id, u.userName, u.firstName, u.lastName, u.email, u.phone, u.address, u.password, u.is_deleted, u.create_at, u.update_at) from User u where u.is_deleted = ?1")
    List<UserInputDto> getAll(Boolean is_deleted);
}
