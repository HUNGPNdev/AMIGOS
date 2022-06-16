package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactDTO {
    private UUID id;

    @Length(max = 250)
    @NotEmpty
    private String name;
    private String email;
    private String phone;
    private String title;
    private Boolean isDeleted;
}
