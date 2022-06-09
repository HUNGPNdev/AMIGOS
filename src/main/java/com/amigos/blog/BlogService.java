package com.amigos.blog;

import com.amigos.blog.model.Blog;
import com.amigos.category.model.CategoryEntity;
import com.amigos.common.ResponseApi;

import java.util.UUID;

public interface BlogService {
    ResponseApi addBlog(Blog blog);
    ResponseApi updateBlog(Blog b);
    ResponseApi getDetailBlog(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getLimit();

}
