package com.amigos.category;

import com.amigos.category.model.CategoryEntity;
import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;

import java.util.UUID;

public interface CategoryService {
    ResponseApi addCategory(CategoryDTO category);
    ResponseApi updateCategory(CategoryDTO category);
    ResponseApi getDetailCategory(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getLimit();
    ResponseApi getCateByCondition();

}
