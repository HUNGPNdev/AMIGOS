package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartDTO
{
    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;
}
