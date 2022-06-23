package com.amigos.dto.customerreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewOuputDto {
    private List<CustomerReviewDto> listCustomerReview;
    private Map<Integer, List<Integer>> CoutStarts;
    private Integer avgStar;
}
