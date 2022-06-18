package com.amigos.orders.model;

import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity
{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private Date createAt;

    @OneToMany(mappedBy = "orderId")
    private List<CartProductSizeEntity> cartProductSizes = new ArrayList<>();
}
