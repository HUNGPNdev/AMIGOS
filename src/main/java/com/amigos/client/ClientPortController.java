package com.amigos.client;

import com.amigos.cartproductsize.CartProductSizeService;
import com.amigos.category.CategoryService;
import com.amigos.common.ResponseApi;
import com.amigos.dto.CartProductSizeDTO;
import com.amigos.dto.CategoryDTO;
import com.amigos.product.ProductService;
import com.amigos.productsize.ProductSizeService;
import com.amigos.productsize.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientPortController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductSizeService productSizeService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductSizeRepository productSizeRepository;

    @Autowired
    CartProductSizeService cartProductSizeService;

    @GetMapping("/categories/limit/{limit}")
    public ResponseEntity<ResponseApi> getLimit(@NotEmpty @PathVariable("limit") int limit) {
        return ResponseEntity.ok(categoryService.getLimit(limit));
    }

    @GetMapping("/products/{cate_id}")
    public ResponseEntity<ResponseApi> getAllProductByCateId(@NotEmpty @PathVariable("cate_id") UUID cateId) {
        return ResponseEntity.ok(productService.getAllProductByCateId(cateId));
    }

    @GetMapping("/products-size/{product_id}")
    public ResponseEntity<ResponseApi> findProductSizeByProductId(@NotEmpty @PathVariable("product_id") UUID productId) {
        return ResponseEntity.ok(productSizeService.findProductSizeByProductId(productId));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ResponseApi> getCateById(@NotEmpty @PathVariable("id") UUID cateId) {
        return ResponseEntity.ok(categoryService.getDetailCategory(cateId));
    }

    @GetMapping("/products-size/limit/{limit}")
    public ResponseEntity<ResponseApi> getProductNewReleases(@NotEmpty @PathVariable("limit") int limit) {
        return ResponseEntity.ok(productService.getProductNewReleases(limit));
    }

    @GetMapping("/products/{productId}/cateId/{cateId}/limit/{limit}")
    public ResponseEntity<ResponseApi> getProductRelatedItem(@NotEmpty @PathVariable("limit") int limit, @NotEmpty @PathVariable("cateId") UUID cateId,
                                                             @NotEmpty @PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(productService.getProductRelatedItem(limit, cateId, productId));
    }

    @GetMapping("/products/featured/limit/{limit}")
    public ResponseEntity<ResponseApi> getProductFeaturedProducts(@NotEmpty @PathVariable("limit") int limit) {
        return ResponseEntity.ok(productService.getProductFeaturedProducts(limit));
    }

    @GetMapping("/cart-product-size/{productSizeId}/count/{count}")
    public ResponseEntity<ResponseApi> addToCart(HttpServletRequest httpServletRequest, @RequestBody CartProductSizeDTO cartProductSize) {
        return ResponseEntity.ok(cartProductSizeService.addToCart(httpServletRequest, cartProductSize));
    }
}
