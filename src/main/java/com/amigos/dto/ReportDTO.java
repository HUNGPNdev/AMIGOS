package com.amigos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private long users = 0;
    private long products = 0;
    private long categories = 0;
    private long orders = 0;
    List<ProductReportDTO> productDetails = new ArrayList<>();
}
