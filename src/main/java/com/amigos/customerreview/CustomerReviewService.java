package com.amigos.customerreview;

import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;
import com.amigos.dto.customerreview.CustomerReviewDto;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface CustomerReviewService {
    ResponseApi addCustomerReview(CustomerReviewDto customerReviewDto, HttpServletRequest httpServletRequest);

   ResponseApi getCustomerReviewByProduct(UUID proId);

    ResponseApi getCustomerReview();
    ResponseApi delete(UUID id);
    ResponseApi CheckCustomerReview(UUID productId);
}
