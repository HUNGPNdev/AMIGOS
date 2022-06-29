package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO
{
    private UUID id;

    private String name;

    private UUID userId;

    private String description;

    private String provider;

    private String title;

    private String guarantee;

    private String part;

    private String image_1;

    private String image_2;

    private String image_3;

    private Boolean isDeleted;

    private UUID cateId;

    private Date createAt;

    private Date updateAt;

    private String cateName;
    private Integer rating ;
    private Integer totalReview;
}
