package com.amigos.blog;

import com.amigos.blog.model.Blog;
import com.amigos.category.model.CategoryEntity;
import com.amigos.common.ResponseApi;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public interface BlogService {
    ResponseApi addBlog(Blog blog);
    ResponseApi addBlogUpdateBlog(MultipartFile image, String blog, HttpServletRequest httpServletRequest) throws IOException;

    ResponseApi updateBlog(Blog b);
    ResponseApi getDetailBlog(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getLimit();
    ResponseApi getListBlog(boolean status);

}
