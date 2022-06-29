package com.amigos.product.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.category.repository.CategoryRepository;
import com.amigos.common.ResponseApi;
import com.amigos.common.UserCommon;
import com.amigos.config.ModelMapperConfig;
import com.amigos.customerreview.model.CustomerReviewEntity;
import com.amigos.dto.ProductDTO;
import com.amigos.dto.customerreview.CustomerReviewDto;
import com.amigos.product.ProductService;
import com.amigos.product.model.ProductEntity;
import com.amigos.product.repository.ProductRepository;
import com.amigos.productsize.repository.ProductSizeRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ServletContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired ModelMapperConfig modelMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductSizeRepository productSizeRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private Environment properties;

    @Override
    @Transactional
    public ResponseApi addProduct(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product, HttpServletRequest httpServletRequest) throws IOException {
        ProductEntity entityCreate = new ProductEntity();
        ProductDTO proD = objectMapper.readValue(product, ProductDTO.class);
        modelMapper.map(proD, entityCreate);
        entityCreate.setCreateAt(new Date());
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        entityCreate.setUserId(createBy);
        entityCreate.setCateId(categoryRepository.findById(proD.getCateId()).get());
        setProductImages(image_1, image_2, image_3, entityCreate);
        entityCreate = productRepository.save(entityCreate);
        proD = modelMapper.map(entityCreate, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi getListProduct(boolean status) {
        List<ProductEntity> products = productRepository.findAllByIsDeleted(status);
        List<ProductDTO> productDTOList = modelMapper.mapAll(products, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productDTOList);
        return rs;
    }

    @Override
    public ResponseApi getProductById(UUID id)
    {
        Optional<ProductEntity> findProduct = productRepository.findById(id);
        if (findProduct.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductDTO proD = modelMapper.map(findProduct.get(), ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi delete(UUID id)
    {
        Optional<ProductEntity> findProduct = productRepository.findById(id);
        if (findProduct.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductEntity productDelete = findProduct.get();
        productDelete.setIsDeleted(Boolean.TRUE);
        productRepository.save(productDelete);
        ProductDTO proD = modelMapper.map(findProduct.get(), ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi getAllProductByCateId(UUID cateId) {
        List<UUID> productIds = productSizeRepository.getProductIdByCateId(cateId);
        List<ProductDTO> productEntities = productRepository.findAllByCateIdAndProductId(productIds);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productEntities);
        return rs;
    }

    @Override
    public ResponseApi getProductNewReleases(int limit)
    {
        List<ProductEntity> resultList = entityManager.createQuery("select p from ProductEntity p where p.isDeleted = false and p.productSizes.size > 0 order by p.createAt desc").setMaxResults(limit).getResultList();
        Map<UUID,Integer> map = new HashMap<UUID,Integer>();
        Map<UUID,Integer> mapSize = new HashMap<UUID,Integer>();
        resultList.forEach(productEntity -> {
            if (productEntity.getProductCustomerReview().size() > 0){
                map.put(productEntity.getId(),AvgStarCustomerReviewByList(productEntity.getProductCustomerReview()));
                mapSize.put(productEntity.getId(),productEntity.getProductCustomerReview().size());
            }

        });
        List<ProductDTO> productEntities = modelMapper.mapAll(resultList, ProductDTO.class);
        productEntities.forEach(productDTO -> {
            if (!map.isEmpty() && map.get(productDTO.getId()) != null){
                productDTO.setRating(map.get(productDTO.getId()));
            }else
                productDTO.setRating(0);

            // size
            if (!mapSize.isEmpty() && mapSize.get(productDTO.getId()) != null){
                productDTO.setTotalReview(mapSize.get(productDTO.getId()));
            }else{
                productDTO.setTotalReview(0);
            }

        });
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productEntities);
        return rs;
    }

    @Override
    public ResponseApi getProductRelatedItem(int limit, UUID cateId, UUID productId) {
        List<UUID> productIds = productSizeRepository.getProductIdByCateId(cateId);
        List<ProductEntity> resultList = entityManager.createQuery("select p from ProductEntity p where p.id in ?1 and p.id <> ?2")
                .setParameter(1, productIds).setParameter(2, productId).setMaxResults(limit).getResultList();
        List<ProductDTO> productEntities = modelMapper.mapAll(resultList, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productEntities);
        return rs;
    }

    @Override
    public ResponseApi getProductFeaturedProducts(int limit) {
        try {
            List<ProductEntity> resultList = entityManager.createQuery("select p from ProductEntity p where p.isDeleted = false and p.productSizes.size > 0").setMaxResults(limit).getResultList();
            List<ProductDTO> productEntities = modelMapper.mapAll(resultList, ProductDTO.class);

            Map<UUID,Integer> map = new HashMap<UUID,Integer>();
            resultList.forEach(productEntity -> {
                if (productEntity.getProductCustomerReview().size() > 0){
                    map.put(productEntity.getId(),AvgStarCustomerReviewByList(productEntity.getProductCustomerReview()));
                }

            });
            productEntities.forEach(productDTO -> {
                if (!map.isEmpty() && map.get(productDTO.getId()) != null){
                    productDTO.setRating(map.get(productDTO.getId()));
                }else
                productDTO.setRating(0);
            });
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productEntities);
            return rs;
        }catch (Exception e){
            throw e;
        }
    }
    private Integer AvgStarCustomerReviewByList(List<CustomerReviewEntity> list){

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
            var result1 =  (starCount.get() /(activeStar.get() / countactiveStar.get()));
            return  result1;
        }catch (Exception x){
            throw x;
        }

    }

    private void setProductImages(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, ProductEntity entity) throws IOException
    {
        String env = properties.getProperty("spring.datasource.url");
        String rootPath = "";
        if(env.equals("jdbc:mysql://localhost:3306/amigos?useSSL=false")) {
            rootPath = "D:/amigos/images/";
        } else if (env.equals("jdbc:mysql://mysqldb/amigos?allowPublicKeyRetrieval=true&useSSL=false")){
            rootPath = "images/";
        } else {
            return;
        }
        Date date = new Date();
        createFolderIfNotExit();
        if(image_1 != null) {
            String fileName = date.getTime()+"1"+image_1.getOriginalFilename();
            Path location = Paths.get(rootPath + fileName);
            try {
                Files.copy(image_1.getInputStream(),
                        location,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
            entity.setImage_1(fileName);
        }
        if(image_2 != null) {
            String fileName = date.getTime()+"2"+image_2.getOriginalFilename();
            Path location = Paths.get(rootPath + fileName);
            try {
                Files.copy(image_2.getInputStream(),
                        location,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
            entity.setImage_2(fileName);
        }
        if(image_3 != null) {
            String fileName = date.getTime()+"3"+image_3.getOriginalFilename();
            Path location = Paths.get(rootPath + fileName);
            try {
                Files.copy(image_3.getInputStream(),
                        location,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
            entity.setImage_3(fileName);
        }
    }

    private void createFolderIfNotExit() {
        Path path = Paths.get("D:/amigos/images");
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.format("Create folder %s", path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
