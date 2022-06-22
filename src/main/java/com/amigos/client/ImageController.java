package com.amigos.client;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {

//    @GetMapping("/images/images")
//    public ResponseEntity<ResponseApi> getImages(@NotEmpty @PathVariable("userName") String userName) {
//        return ResponseEntity.ok(orderService.findAllByUserId(httpServletRequest));
//    }
}
