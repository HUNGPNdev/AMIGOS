package com.amigos.productsize;

import com.amigos.common.ResponseApi;
import com.amigos.dto.ProductSizeDTO;

import java.util.UUID;

public interface ProductSizeService
{
    ResponseApi addProductSize(ProductSizeDTO productSizeDTO);
    ResponseApi getDetail(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getAllByStatus(boolean status);
    ResponseApi getAllProductSizeByCateId(UUID cateId);
    ResponseApi findProductSizeByProductId(UUID productId);
}
