package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeDTO
{
    private UUID id;

    private UUID productId;

    private double price;

    private String discount;

    private float count;

    private UUID sizeId;

    private String name;

    private String description;

    private String provider;

    private String title;

    private String guarantee;

    private String part;

    private String image_1;

    private String image_2;

    private String image_3;

    private Boolean isDeleted;
}
