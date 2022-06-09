package com.amigos.blog.impl;

import com.amigos.blog.BlogService;
import com.amigos.blog.model.Blog;
import com.amigos.blog.repository.BlogRepository;
import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.blog.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    @Override
    public ResponseApi addBlog(Blog blog) {
        blog.setIs_deleted(Boolean.FALSE);
        Blog blogEntity = blogRepository.save(blog);
        BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), blogDTO);
    return rs;
    }

    @Override
    public ResponseApi updateBlog(Blog b) {
        Optional<Blog> find = blogRepository.findById(b.getId());
        if(!find.isEmpty()) {
            Blog blogEntity = blogRepository.save(b);
            BlogDTO blogDTO = modelMapper.map(b, BlogDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), blogDTO);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getDetailBlog(UUID id) {
        Optional<Blog> blogEntity = blogRepository.findById(id);
        if(!blogEntity.isEmpty()) {
            BlogDTO map = modelMapper.map(blogEntity.get(), BlogDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi delete(UUID id) {
                Optional<Blog> blogEntity = blogRepository.findById(id);
        if(!blogEntity.isEmpty()) {
            Blog entity = blogEntity.get();
            entity.setIs_deleted(true);
            blogRepository.save(entity);
            BlogDTO map = modelMapper.map(entity, BlogDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }

    }

    @Override
    public ResponseApi getLimit() {
                List<Blog> blogEntity = blogRepository.getLimit(Boolean.FALSE, 5);
        List<BlogDTO> categoryDTOS = modelMapper.mapAll(blogEntity, BlogDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryDTOS);
        return rs;
    }


}
