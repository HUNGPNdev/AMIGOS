package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDTO
{
    private UUID id;

    private UUID userId;

    private UUID proId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private Date createAt;

    private List<CartProductSizeDTO> cartProductSizes;

    private Double totalPrice = 0.0;

    private Boolean isDeleted;
}
