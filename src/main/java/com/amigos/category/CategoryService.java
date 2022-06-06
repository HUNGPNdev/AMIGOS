package com.amigos.category;

import com.amigos.category.model.CategoryEntity;
import com.amigos.common.ResponseApi;

import java.util.UUID;

public interface CategoryService {
    ResponseApi addCategory(CategoryEntity category);
    ResponseApi updateCategory(CategoryEntity category);
    ResponseApi getDetailCategory(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getLimit();
    ResponseApi getCateByCondition();

}
