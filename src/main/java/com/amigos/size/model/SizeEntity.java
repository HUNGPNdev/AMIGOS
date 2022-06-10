package com.amigos.size.model;

import com.amigos.productsize.model.ProductSizeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "size")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16, unique = true, nullable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "sizeId")
    private List<ProductSizeEntity> productSizes = new ArrayList<>();
}
