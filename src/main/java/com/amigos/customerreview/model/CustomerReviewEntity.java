package com.amigos.customerreview.model;

import com.amigos.product.model.ProductEntity;
import com.amigos.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "customer_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerReviewEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productId;
    private  Integer rating;
    private String comment;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "create_at")
    private Date createAt;
}
