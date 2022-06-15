package com.amigos.dto;

import com.amigos.cartproductsize.model.EnumStatusCart;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    private double price;

    private UUID proId;

    private String proName;

    private float productSizeDiscount;

    private String image_1;

}
