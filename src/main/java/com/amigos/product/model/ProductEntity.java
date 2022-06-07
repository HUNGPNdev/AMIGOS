package com.amigos.product.model;

import com.amigos.category.model.CategoryEntity;
import com.amigos.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @JoinColumn(name = "cate_id", nullable = false)
    private CategoryEntity cateId;

    private Date createAt;

    private Date updateAt;
}
