package com.amigos.dto.customerreview;

import com.amigos.product.model.ProductEntity;
import com.amigos.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewDto {
    private UUID id;
    private String title;
    private UUID userId;
    private UUID productId;
    private  Integer rating;
    private String comment;
    private Boolean isDeleted;
    private Date createAt;
    private String productName;
    private String userName;
}
