package com.amigos.cartproductsize;

import com.amigos.common.ResponseApi;
import com.amigos.dto.CartProductSizeDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface CartProductSizeService
{
    ResponseApi addToCart(HttpServletRequest httpServletRequest, CartProductSizeDTO cartProductSize);

}
