package com.amigos.orders.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.cartproductsize.model.EnumStatusCart;
import com.amigos.cartproductsize.repository.CartProductSizeRepository;
import com.amigos.common.ResponseApi;
import com.amigos.common.UserCommon;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.OrderCartDTO;
import com.amigos.orders.OrderService;
import com.amigos.orders.model.OrderEntity;
import com.amigos.orders.repository.OrderRepository;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JwtProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartProductSizeRepository cartProductSizeRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    @Override
    @Transactional
    public ResponseApi goToOrders(OrderCartDTO orderCartDTO, HttpServletRequest httpServletRequest)
    {
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        List<CartProductSizeEntity> cartProductSizes = cartProductSizeRepository.findAllByUserId_IdAndStatus(createBy.getId(), EnumStatusCart.SHOPPING_CART);
        if (cartProductSizes.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        OrderEntity orderEntity = new OrderEntity();
        modelMapper.map(orderCartDTO, orderEntity);
        orderEntity.setUserId(createBy);
        orderEntity.setCreateAt(new Date());
        for (CartProductSizeEntity c: cartProductSizes) {
            ProductSizeEntity productSize = c.getProductSizeId();
            c.setPrice(productSize.getDiscount());
            c.setCreateAt(new Date());
            c.setOrderId(orderEntity);
            c.setStatus(EnumStatusCart.WAITING_FOR_CONFIRMATION);
            int proCount = productSize.getCount();
            if(c.getCount() > proCount) {
                c.setCount(proCount);
            }
            proCount = productSize.getCount() - c.getCount();
            productSize.setCount(proCount);
            if(proCount == 0) {
                productSize.setIsDeleted(Boolean.TRUE);
            }
        }
        orderRepository.save(orderEntity);
        cartProductSizeRepository.saveAll(cartProductSizes);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        return rs;
    }
}
