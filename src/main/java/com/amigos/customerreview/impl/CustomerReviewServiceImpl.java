package com.amigos.customerreview.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.cartproductsize.model.EnumStatusCart;
import com.amigos.cartproductsize.repository.CartProductSizeRepository;
import com.amigos.common.ResponseApi;
import com.amigos.common.UserCommon;
import com.amigos.config.ModelMapperConfig;
import com.amigos.customerreview.CustomerReviewService;
import com.amigos.customerreview.model.CustomerReviewEntity;
import com.amigos.customerreview.repository.CustomerReviewRepository;
import com.amigos.dto.CategoryDTO;
import com.amigos.dto.customerreview.CustomerReviewDto;
import com.amigos.dto.customerreview.CustomerReviewOuputDto;
import com.amigos.product.model.ProductEntity;
import com.amigos.product.repository.ProductRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class CustomerReviewServiceImpl implements CustomerReviewService {
    @Autowired
    CustomerReviewRepository customerReviewRepository;
    @Autowired
    CartProductSizeRepository cartProductSizeRepository;
    @Autowired
    ModelMapperConfig modelMapper;
    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public ResponseApi addCustomerReview(CustomerReviewDto customerRv, HttpServletRequest httpServletRequest) {
        CustomerReviewEntity customerReviewEntity = new CustomerReviewEntity();
        customerReviewEntity.setComment (customerRv.getComment());
        customerReviewEntity.setIsDeleted(Boolean.FALSE);
        customerReviewEntity.setCreateAt(new Date());
        customerReviewEntity.setRating(customerRv.getRating());
        customerReviewEntity.setTitle(customerRv.getTitle());
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        customerReviewEntity.setUserId(createBy);
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(customerRv.getProductId());
            if(productEntity.isEmpty()){
                ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
                return rs;
            }
            customerReviewEntity.setProductId(productEntity.get());
        }catch (Exception e){
            throw e;
        }



        CustomerReviewEntity rp = customerReviewRepository.save(customerReviewEntity);
        CategoryDTO categoryDTO = modelMapper.map(rp, CategoryDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryDTO);
        return rs;
    }

    @Override
    public ResponseApi getCustomerReviewByProduct(UUID proId) {
        try {
            var listCustomerReview = customerReviewRepository.getCustomerReviewByCondition(proId)
                    .stream().map(el -> new CustomerReviewDto(
                            el.getId(),
                            el.getTitle(),
                            el.getUserId().getId(),
                            el.getProductId().getId(),
                            el.getRating(),
                            el.getComment(),
                            el.getIsDeleted(),
                            el.getCreateAt(),
                            el.getProductId().getName(),
                            el.getUserId().getUserName()
                        )).collect(Collectors.toList());
            Map<Integer, List<Integer>> result = listCustomerReview.stream()
                        .collect(Collectors.groupingBy(p->p.getRating(), Collectors.mapping(p-> p.getRating() , Collectors.toList())));
           Integer avg = AvgStarCustomerReviewByList(listCustomerReview);

            CustomerReviewOuputDto customerReviewOuputDto = new CustomerReviewOuputDto(listCustomerReview,result,avg);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), customerReviewOuputDto);

            return rs;
        }catch (Exception e){
            throw  e;
        }

    }

    @Override
    public ResponseApi getCustomerReview() {
        try {
            var listCustomerReview = customerReviewRepository.getCustomerReview()
                    .stream().map(el -> new CustomerReviewDto(
                            el.getId(),
                            el.getTitle(),
                            el.getUserId().getId(),
                            el.getProductId().getId(),
                            el.getRating(),
                            el.getComment(),
                            el.getIsDeleted(),
                            el.getCreateAt(),
                            el.getProductId().getName(),
                            el.getUserId().getUserName()
                    )).collect(Collectors.toList());



            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), listCustomerReview);

            return rs;
        }catch (Exception e){
            throw  e;
        }
    }

    @Override
    public ResponseApi delete(UUID id) {
        Optional<CustomerReviewEntity> customerReviewEntity = customerReviewRepository.findById(id);
        if(!customerReviewEntity.isEmpty()) {
            CustomerReviewEntity entity = customerReviewEntity.get();
            entity.setIsDeleted(true);
            customerReviewRepository.save(entity);

            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), entity);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi CheckCustomerReview(UUID productId, HttpServletRequest httpServletRequest) {
        try {
            User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
            if(createBy == null) {
                ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), false);
                return rs;
            }
          var product  = (productRepository.findById(productId)).get();
            AtomicBoolean check = new AtomicBoolean(false);
            if (product != null && product.getProductSizes().size() > 0){
                product.getProductSizes().forEach(prSize -> {
                  boolean exits =  cartProductSizeRepository.existsByUserId_IdAndStatusAndProductSizeId_Id(createBy.getId(), EnumStatusCart.DELIVERED,prSize.getId());
                  if (exits){
                      check.set(true);
                      return;
                  }
                });
            }

            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), check.get());
            return  rs;
        }catch (Exception e){
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), false);
            return rs;
        }

    }

    private Integer AvgStarCustomerReviewByList(List<CustomerReviewDto> list){

       try {
           Map<Integer, List<Integer>> result = list.stream()
                   .collect(Collectors.groupingBy(p->p.getRating(), Collectors.mapping(p-> p.getRating() , Collectors.toList())));

           AtomicInteger starCount = new AtomicInteger();
           AtomicInteger activeStar = new AtomicInteger();
           AtomicInteger countactiveStar = new AtomicInteger();
           result.forEach((integer, integers) -> {
               Integer customerRvStar = integer.intValue();
               var countSize  =  integers.size();

               if(customerRvStar.equals(1)){
                   activeStar.addAndGet(1);
                   countactiveStar.getAndIncrement();
                   starCount.addAndGet((1 * countSize));
               }else if(customerRvStar.equals(2)){
                   activeStar.addAndGet(2);
                   countactiveStar.getAndIncrement();
                   starCount.addAndGet((2 * countSize));
               }else if(customerRvStar.equals(3)){
                   activeStar.addAndGet(3);
                   countactiveStar.getAndIncrement();
                   starCount.addAndGet((3 * countSize));
               }else if(customerRvStar.equals(4)){
                   activeStar.addAndGet(4);
                   countactiveStar.getAndIncrement();
                   starCount.addAndGet((4 * countSize));
               }else if(customerRvStar.equals(5)){
                   activeStar.addAndGet(5);
                   countactiveStar.getAndIncrement();
                   starCount.addAndGet((5 * countSize));
               }

           });
           int result1 = 0;
               result1 = (starCount.get()/countactiveStar.get());
           return  result1;
       }catch (Exception x){
           throw x;
       }

    }


}
