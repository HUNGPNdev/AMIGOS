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
import com.amigos.product.model.ProductEntity;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

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
        orderEntity.setIsDeleted(Boolean.FALSE);
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
        orderEntity.setCartProductSizes(cartProductSizes);
        orderRepository.save(orderEntity);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        return rs;
    }

    @Override
    public ResponseApi findAllByUserId(HttpServletRequest httpServletRequest) {
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        List<OrderEntity> orders = orderRepository.findAllByUserIdAndCartOrderNotNull(createBy.getId());
        List<OrderCartDTO> orderCartDTOS = modelMapper.mapAll(orders, OrderCartDTO.class);
        orderSetParameter(orders, orderCartDTOS);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), orderCartDTOS);
        return rs;
    }

    @Override
    public ResponseApi getAlls(boolean isDeleted) {
        List<OrderEntity> orders = orderRepository.findAllByIsDeleted(isDeleted);
        List<OrderCartDTO> orderCartDTOS = modelMapper.mapAll(orders, OrderCartDTO.class);
        orderSetParameter(orders, orderCartDTOS);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), orderCartDTOS);
        return rs;
    }

    @Override
    public ResponseApi orderUpdateStatus(UUID orderId, EnumStatusCart status) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        OrderEntity orderEntity = order.get();
        for (CartProductSizeEntity cart: orderEntity.getCartProductSizes()) {
            cart.setStatus(status);
        }
        orderRepository.save(orderEntity);
        OrderCartDTO orderCartDTO = new OrderCartDTO();
        modelMapper.map(orderEntity, orderCartDTO);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), orderCartDTO);
        return rs;
    }

    @Override
    public ResponseApi deleteById(UUID orderId)
    {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        OrderEntity orderEntity = order.get();
        orderEntity.setIsDeleted(Boolean.TRUE);
        orderRepository.save(orderEntity);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        return rs;
    }

    private void orderSetParameter(List<OrderEntity> orders, List<OrderCartDTO> orderCartDTOS) {
        orderCartDTOS.forEach(c -> {
            c.getCartProductSizes().forEach(t -> {
                Optional<OrderEntity> order = orders.stream().filter(f -> f.getId() == c.getId()).findFirst();
                if(!order.isEmpty()) {
                    Optional<CartProductSizeEntity> cartProduct = order.get().getCartProductSizes().stream().filter(f -> t.getId() == f.getId()).findFirst();
                    if(!cartProduct.isEmpty()) {
                        ProductEntity product = cartProduct.get().getProductSizeId().getProductId();
                        t.setImage_1(product.getImage_1());
                        c.setTotalPrice(c.getTotalPrice() + t.getPrice()*t.getCount());
                        t.setProName(product.getName());
                        t.setProId(product.getId());
                    }
                }
            });
        });
    }
}
