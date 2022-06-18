package com.amigos.cartproductsize.model;

import com.amigos.orders.model.OrderEntity;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
public class CartProductSizeEntity
{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_size_id")
    private ProductSizeEntity productSizeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    private int count;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumStatusCart status;

    private Date createAt;

    private double price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderId;

}
