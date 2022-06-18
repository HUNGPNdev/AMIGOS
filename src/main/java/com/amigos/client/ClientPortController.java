package com.amigos.client;

import com.amigos.cartproductsize.CartProductSizeService;
import com.amigos.category.CategoryService;
import com.amigos.common.ResponseApi;
import com.amigos.dto.CartProductSizeDTO;
import com.amigos.dto.OrderCartDTO;
import com.amigos.orders.OrderService;
import com.amigos.product.ProductService;
import com.amigos.productsize.ProductSizeService;
import com.amigos.productsize.repository.ProductSizeRepository;
import com.amigos.user.service.UserService;
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

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

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

    @PostMapping("/cart-product-size/add-to-cart")
    public ResponseEntity<ResponseApi> addToCart(@RequestBody CartProductSizeDTO cartProductSize, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(cartProductSizeService.addToCart(httpServletRequest, cartProductSize));
    }

    @GetMapping("/cart-product-size")
    public ResponseEntity<ResponseApi> getCartByUser(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(cartProductSizeService.getCartByUser(httpServletRequest));
    }

    @DeleteMapping("/cart-product-size/{cartId}")
    public ResponseEntity<ResponseApi> deleteCart(@NotEmpty @PathVariable("cartId") UUID cartId) {
        return ResponseEntity.ok(cartProductSizeService.deleteCart(cartId));
    }

    @PutMapping("/cart-product-size")
    public ResponseEntity<ResponseApi> updateCart(@RequestBody CartProductSizeDTO cartProductSize) {
        return ResponseEntity.ok(cartProductSizeService.updateCart(cartProductSize));
    }

    @GetMapping("/cart-product-size/count")
    public ResponseEntity<ResponseApi> countCartByUserId(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(cartProductSizeService.countCartByUserId(httpServletRequest));
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<ResponseApi> getUserDetailByUserName(@NotEmpty @PathVariable("userName") String userName) {
        return ResponseEntity.ok(userService.findByUserName(userName));
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseApi> goToOrders(@RequestBody OrderCartDTO orderCartDTO, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(orderService.goToOrders(orderCartDTO, httpServletRequest));
    }

    @GetMapping("/cart-product-size/ordered")
    public ResponseEntity<ResponseApi> getCartOrderedByUser(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(cartProductSizeService.getCartOrderedByUser(httpServletRequest));
    }
}
