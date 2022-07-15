package com.amigos.blog.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.blog.BlogService;
import com.amigos.blog.model.Blog;
import com.amigos.blog.repository.BlogRepository;
import com.amigos.common.ResponseApi;
import com.amigos.common.UserCommon;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.blog.BlogDTO;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
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

    @Autowired
    private Environment properties;

    @Override
    public ResponseApi addBlog(Blog blog) {
        blog.setIs_deleted(Boolean.FALSE);
        Blog blogEntity = blogRepository.save(blog);
        BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), blogDTO);
    return rs;
    }
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServletContext context;
    @Override
    public ResponseApi addBlogUpdateBlog(MultipartFile image, String blog, HttpServletRequest httpServletRequest) throws IOException {
        try {
            Blog entityCreate = new Blog();
            BlogDTO blD = objectMapper.readValue(blog, BlogDTO.class);
            modelMapper.map(blD, entityCreate);
            entityCreate.setCreate_at(new Date());
            User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
            if(createBy == null) {
                ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
                return rs;
            }
            entityCreate.setUserId(createBy.getId());
            setProductImages(image, entityCreate);
            entityCreate.setIs_deleted(false);
            entityCreate = blogRepository.save(entityCreate);
            blD = modelMapper.map(entityCreate, BlogDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), blD);
            return rs;
        }catch (Exception e){
            throw e;
        }
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

    @Override
    public ResponseApi getListBlog(boolean status) {
        List<Blog> blogs = blogRepository.findAllByIsDeleted(status);
        List<BlogDTO> productDTOList = modelMapper.mapAll(blogs, BlogDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productDTOList);
        return  rs;
    }

    private void setProductImages(MultipartFile image,  Blog entity) throws IOException
    {
        Date date = new Date();
        String env = properties.getProperty("spring.datasource.url");
        String rootPath = "";
        if(env.equals("jdbc:mysql://localhost:3306/amigos?useSSL=false")) {
            rootPath = "D:/amigos/images/";
        } else if (env.equals("jdbc:mysql://mysqldb/amigos?allowPublicKeyRetrieval=true&useSSL=false")){
            rootPath = "images/";
        } else {
            return;
        }
        if(image != null) {
            String fileName = date.getTime()+"1"+image.getOriginalFilename();
            Path location = Paths.get(rootPath + fileName);
            try {
                Files.copy(image.getInputStream(),
                        location,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
            entity.setImage(fileName);
        }
    }
}
