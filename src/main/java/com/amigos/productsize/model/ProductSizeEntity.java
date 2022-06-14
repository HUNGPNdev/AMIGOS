package com.amigos.productsize.model;

import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.product.model.ProductEntity;
import com.amigos.size.model.SizeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_size")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSizeEntity
{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productId;

    private double price;

    private float discount;

    private int count;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeEntity sizeId;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "productSizeId")
    private List<CartProductSizeEntity> cartProductSizes = new ArrayList<>();
}
