package com.amigos.category.impl;

import com.amigos.category.CategoryService;
import com.amigos.category.model.CategoryEntity;
import com.amigos.category.repository.CategoryRepository;
import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    @Override
    public ResponseApi addCategory(CategoryEntity category) {
        category.setIsDeleted(Boolean.FALSE);
        CategoryEntity categoryEntity = categoryRepository.save(category);
        CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryDTO);
        return rs;
    }

    @Override
    public ResponseApi updateCategory(CategoryEntity category) {
        Optional<CategoryEntity> find = categoryRepository.findById(category.getId());
        if(!find.isEmpty()) {
            CategoryEntity categoryEntity = categoryRepository.save(category);
            CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryDTO);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getDetailCategory(UUID id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(!categoryEntity.isEmpty()) {
            CategoryDTO map = modelMapper.map(categoryEntity.get(), CategoryDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi delete(UUID id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(!categoryEntity.isEmpty()) {
            CategoryEntity entity = categoryEntity.get();
            entity.setIsDeleted(true);
            categoryRepository.save(entity);
            CategoryDTO map = modelMapper.map(entity, CategoryDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getAll() {
        List<CategoryDTO> categoryEntity = categoryRepository.getAll(Boolean.FALSE);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), modelMapper.mapAll(categoryEntity, CategoryDTO.class));
        return rs;
    }

}
