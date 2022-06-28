package com.amigos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/images/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {
    @Autowired
    private Environment properties;

    @GetMapping("{images}")
    public byte[] getImages(@NotEmpty @PathVariable("images") String images) throws IOException {
        String env = properties.getProperty("spring.datasource.url");
        String rootPath = "";
        if(env.equals("jdbc:mysql://localhost:3306/amigos?useSSL=false")) {
            rootPath = "D:/amigos/images/";
        } else if (env.equals("jdbc:mysql://mysqldb/amigos?allowPublicKeyRetrieval=true&useSSL=false")){
            rootPath = "/images/";
        } else {
            return null;
        }
        File file = ResourceUtils.getFile(rootPath + images);
        if(file.exists()) {
            byte[] fileData = Files.readAllBytes(file.toPath());
            return fileData;
        }
        return null;
    }
}
