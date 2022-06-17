package com.amigos.orders;

import com.amigos.common.ResponseApi;
import com.amigos.dto.OrderCartDTO;

import javax.servlet.http.HttpServletRequest;

public interface OrderService
{
    ResponseApi goToOrders(OrderCartDTO orderCartDTO, HttpServletRequest httpServletRequest);
}
