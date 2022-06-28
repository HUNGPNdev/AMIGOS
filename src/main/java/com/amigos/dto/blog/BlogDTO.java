package com.amigos.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogDTO {
    private UUID id;

    private String image;
    private  String description;
    private String title;
    private UUID userId;
    private Boolean is_deleted;
    private Date create_at;
}
