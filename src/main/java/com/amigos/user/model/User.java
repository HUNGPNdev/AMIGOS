package com.amigos.user.model;

import com.amigos.cartproductsize.model.CartProductSizeEntity;
import com.amigos.product.model.ProductEntity;
import com.amigos.role.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", unique = true, nullable = false)
    private String lastName;

    private String email;

    @NotNull
    @JsonIgnore
    private String password;

    private String phone;

    private String address;


    private Boolean is_deleted;

    private Date create_at;

    private Date update_at;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonManagedReference
    private List<Role> roles = new ArrayList<>();


    public User(String userName, String firstName, String lastName, String email, String phone, String address, String password, Boolean is_deleted) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.is_deleted = is_deleted;
    }

    @OneToMany(mappedBy = "userId")
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "userId")
    private List<CartProductSizeEntity> carts = new ArrayList<>();
}
