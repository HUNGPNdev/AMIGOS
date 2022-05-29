package com.amigos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    @JsonIgnore
    private String password;

    private List<RoleDTO> roles;
}
