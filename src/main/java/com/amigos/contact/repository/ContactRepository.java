package com.amigos.contact.repository;

import com.amigos.contact.model.ContactEntity;
import com.amigos.dto.ContactDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
    @Query("select new com.amigos.dto.ContactDTO(c.id, c.name, c.email, c.phone, c.title, c.isDeleted) from ContactEntity c where c.isDeleted = ?1")
    List<ContactDTO> getAll(Boolean isDelete);
}
