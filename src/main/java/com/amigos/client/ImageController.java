package com.amigos.client;

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

    @GetMapping("{images}")
    public byte[] getImages(@NotEmpty @PathVariable("images") String images) throws IOException {
        File file = ResourceUtils.getFile("/images/" + images);
        System.out.println("File:"+file.getName());
        if(file.exists()) {
            byte[] fileData = Files.readAllBytes(file.toPath());
            return fileData;
        }
        return null;
    }
}
