package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

    private UUID id;

    private String name;

    private Boolean isDeleted;
}
