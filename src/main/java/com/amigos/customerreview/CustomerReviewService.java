package com.amigos.customerreview;

import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;
import com.amigos.dto.customerreview.CustomerReviewDto;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface CustomerReviewService {
    ResponseApi addCustomerReview(CustomerReviewDto customerReviewDto, HttpServletRequest httpServletRequest);
//    ResponseApi updateCategory(CategoryDTO category);
   ResponseApi getCustomerReviewByProduct(UUID proId);
//    ResponseApi delete(UUID id);
//    ResponseApi getLimit(int limit);
//    ResponseApi getCateByCondition();

}
