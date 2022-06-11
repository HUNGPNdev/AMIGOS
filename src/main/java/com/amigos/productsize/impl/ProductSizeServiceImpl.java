package com.amigos.productsize.impl;

import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.ProductSizeDTO;
import com.amigos.product.model.ProductEntity;
import com.amigos.product.repository.ProductRepository;
import com.amigos.productsize.ProductSizeService;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.productsize.repository.ProductSizeRepository;
import com.amigos.size.model.SizeEntity;
import com.amigos.size.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class ProductSizeServiceImpl implements ProductSizeService
{
    @Autowired
    ProductSizeRepository productSizeRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Override
    public ResponseApi addProductSize(ProductSizeDTO productSizeDTO)
    {
        ProductSizeEntity productSizeCreate = new ProductSizeEntity();
        modelMapper.map(productSizeDTO, productSizeCreate);
        Optional<ProductEntity> product = productRepository.findById(productSizeDTO.getProductId());
        if (product.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        Optional<SizeEntity> size = sizeRepository.findById(productSizeDTO.getSizeId());
        if (size.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        productSizeCreate.setProductId(product.get());
        productSizeCreate.setSizeId(size.get());
        productSizeCreate = productSizeRepository.save(productSizeCreate);

        ProductSizeDTO productSizeResponse = modelMapper.map(productSizeCreate, ProductSizeDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productSizeResponse);
        return rs;
    }

    @Override
    public ResponseApi getDetail(UUID id)
    {
        Optional<ProductSizeEntity> productSize = productSizeRepository.findById(id);
        if (productSize.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductSizeEntity productSizeEntity = productSize.get();
        ProductSizeDTO productSizeResponse = modelMapper.map(productSizeEntity, ProductSizeDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productSizeResponse);
        return rs;
    }

    @Override
    public ResponseApi delete(UUID id)
    {
        Optional<ProductSizeEntity> productSize = productSizeRepository.findById(id);
        if (productSize.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductSizeEntity productSizeEntity = productSize.get();
        productSizeEntity.setIsDeleted(Boolean.TRUE);
        productSizeEntity = productSizeRepository.save(productSizeEntity);
        ProductSizeDTO productSizeResponse = modelMapper.map(productSizeEntity, ProductSizeDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productSizeResponse);
        return rs;
    }

    @Override
    public ResponseApi getAllByStatus(boolean status)
    {
        List<ProductSizeDTO> productSizeEntities = productSizeRepository.getAllByStatus(status);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productSizeEntities);
        return rs;
    }
}
