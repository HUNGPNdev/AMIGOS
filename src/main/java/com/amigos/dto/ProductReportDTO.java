package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReportDTO {
    private String productName;
    private String categoryName;
    private long inStock = 0;
    private long sold = 0;
}
