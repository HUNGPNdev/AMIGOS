package com.amigos.product.model;

import com.amigos.category.model.CategoryEntity;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.size.model.SizeEntity;
import com.amigos.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    private String description;

    private String provider;

    private String title;

    private String guarantee;

    private String part;

    private String image_1;

    private String image_2;

    private String image_3;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    private CategoryEntity cateId;

    @OneToMany(mappedBy = "productId")
    private List<ProductSizeEntity> productSizes = new ArrayList<>();
    @OneToMany(mappedBy = "productId")
    private List<ProductSizeEntity> productCustomerReview = new ArrayList<>();
    private Date createAt;

    private Date updateAt;
}
