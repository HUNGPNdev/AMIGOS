package com.amigos.dto;

import com.amigos.role.model.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleInputDTO {

    private UUID id;
    private String name;


}
