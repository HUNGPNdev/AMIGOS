package com.amigos.dto;

import com.amigos.cartproductsize.model.EnumStatusCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartProductSizeDTO
{
    private UUID id;

    private UUID productSizeId;

    private UUID userId;

    private int count;

    private String address;

    @Enumerated(EnumType.STRING)
    private EnumStatusCart status;

    private Date createAt;
}
