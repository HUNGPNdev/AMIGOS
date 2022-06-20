package com.amigos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInputDto {
    private UUID id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;


    private String password;

    private Boolean is_deleted;

    private Date create_at;

    private Date update_at;
}
