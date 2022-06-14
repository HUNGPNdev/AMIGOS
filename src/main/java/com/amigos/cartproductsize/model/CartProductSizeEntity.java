package com.amigos.cartproductsize.model;

import com.amigos.productsize.model.ProductSizeEntity;
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
@Table(name = "cart_product_size")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartProductSizeEntity
{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ProductSizeEntity productSizeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    private int count;

    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumStatusCart status;

    private Boolean isDeleted;

    private Date createAt;
}
