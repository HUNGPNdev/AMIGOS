package com.amigos.cartproductsize.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.cartproductsize.CartProductSizeService;
import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.cartproductsize.model.EnumStatusCart;
import com.amigos.cartproductsize.repository.CartProductSizeRepository;
import com.amigos.common.ResponseApi;
import com.amigos.common.UserCommon;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.CartProductSizeDTO;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.productsize.repository.ProductSizeRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class CartProductSizeServiceImpl implements CartProductSizeService
{
    @Autowired
    ProductSizeRepository productSizeRepository;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired UserRepository userRepository;

    @Autowired ModelMapperConfig modelMapper;

    @Autowired CartProductSizeRepository cartProductSizeRepository;

    @Override
    @Transactional
    public ResponseApi addToCart(HttpServletRequest httpServletRequest, CartProductSizeDTO cartProductSize)
    {
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        CartProductSizeEntity cartProductSizeEntity = cartProductSizeRepository.findCartByProductSizeAndUserId(cartProductSize.getProductSizeId(),
                createBy.getId(), EnumStatusCart.SHOPPING_CART);
        if(cartProductSizeEntity != null) {
            cartProductSizeEntity.setCount(cartProductSizeEntity.getCount() + cartProductSize.getCount());
            cartProductSizeEntity = cartProductSizeRepository.save(cartProductSizeEntity);
            modelMapper.map(cartProductSizeEntity, cartProductSize);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), cartProductSize);
            return rs;
        }
        cartProductSizeEntity = new CartProductSizeEntity();
        Optional<ProductSizeEntity> productSize = productSizeRepository.findById(cartProductSize.getProductSizeId());
        if(productSize.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        cartProductSizeEntity.setCount(cartProductSize.getCount());
        cartProductSizeEntity.setProductSizeId(productSize.get());
        cartProductSizeEntity.setStatus(EnumStatusCart.SHOPPING_CART);
        cartProductSizeEntity.setUserId(createBy);
        cartProductSizeEntity = cartProductSizeRepository.save(cartProductSizeEntity);
        modelMapper.map(cartProductSizeEntity, cartProductSize);

        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), cartProductSize);
        return rs;
    }

    @Override
    public ResponseApi getCartByUser(HttpServletRequest httpServletRequest)
    {
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        List<CartProductSizeDTO> cartProductSizes = cartProductSizeRepository.findCartByUserIdAndStatus(createBy.getId(), EnumStatusCart.SHOPPING_CART);
        Double totalPrice = 0.0;
        for (CartProductSizeDTO c: cartProductSizes) {
            totalPrice += c.getCount() * c.getPrice();
        }
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), String.valueOf(totalPrice), cartProductSizes);
        return rs;
    }

    @Override
    @Transactional
    public ResponseApi deleteCart(UUID cartId)
    {
        Optional<CartProductSizeEntity> cartProductSize = cartProductSizeRepository.findById(cartId);
        if(cartProductSize.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        cartProductSizeRepository.delete(cartProductSize.get());
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        return rs;
    }

    @Override
    public ResponseApi updateCart(CartProductSizeDTO cartProductSize)
    {
        CartProductSizeEntity cartProductSizeEntity = cartProductSizeRepository.findCartByProductSizeAndUserId(cartProductSize.getProductSizeId(),
                cartProductSize.getUserId(), EnumStatusCart.SHOPPING_CART);
        if(cartProductSizeEntity == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        cartProductSizeEntity.setCount(cartProductSize.getCount());
        cartProductSizeRepository.save(cartProductSizeEntity);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        return rs;
    }

    @Override
    public ResponseApi countCartByUserId(HttpServletRequest httpServletRequest)
    {
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        int count = cartProductSizeRepository.countAllByUserId_IdAndStatus(createBy.getId(), EnumStatusCart.SHOPPING_CART);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), count);
        return rs;
    }
}
