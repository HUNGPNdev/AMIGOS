package com.amigos.orders;

import com.amigos.cartproductsize.model.EnumStatusCart;
import com.amigos.common.ResponseApi;
import com.amigos.dto.OrderCartDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface OrderService
{
    ResponseApi goToOrders(OrderCartDTO orderCartDTO, HttpServletRequest httpServletRequest);

    ResponseApi findAllByUserId(HttpServletRequest httpServletRequest);

    ResponseApi getAlls(boolean isDeleted);

    ResponseApi orderUpdateStatus(UUID orderId, EnumStatusCart status);

    ResponseApi deleteById(UUID orderId);
}
