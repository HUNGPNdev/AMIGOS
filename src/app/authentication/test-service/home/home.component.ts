import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartProductSize } from '../../entity/client-port/cart-product-size';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { Product } from '../../entity/client-port/product';
import { ProductSize } from '../../entity/client-port/product-size';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  productNewReleases: Product[];
  productFeaturedProducts: Product[];
  info: any;
  productSize: ProductSize = new ProductSize();
  productSizes: ProductSize[];
  quantity = 1;
  cartProductSize: CartProductSize = new CartProductSize();

  constructor(private token: TokenStorageService,
    private router: Router,
    private clientPortService: ClientPortService) { }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
    this.getProductFeaturedProducts();
    this.getProductNewReleases();
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }

  getProductNewReleases() {
    var limit = 4;
    this.clientPortService.getProductNewReleases(limit).subscribe(data => {
      this.productNewReleases = data.data;
    }, error => console.log(error))
  }

  findProductSizeBySizeName(sizeName: String) {
    for (var i = 0; i <= this.productSizes.length; i++) {
      if (this.productSizes[i].sizeName == sizeName) {
        this.productSize = this.productSizes[i];
        break;
      }
    }
  }

  findProductSizeByProductId(id: number) {
    this.clientPortService.findProductSizeByProductId(id).subscribe(data => {
      this.productSizes = data.data;
      this.productSize = this.productSizes[0];
    }, error => console.log(error))
  }

  getProductFeaturedProducts() {
    var limit = 8;
    this.clientPortService.getProductFeaturedProducts(limit).subscribe(data => {
      this.productFeaturedProducts = data.data;
    }, error => console.log(error))
  }

  onChangeSort(event) {
    var value = event.target.value;
    this.quantity = value;
  }

  addToCart() {
    if (this.token.getUsername()) {
      this.cartProductSize.count = this.quantity;
      this.cartProductSize.productSizeId = this.productSize.id;
      this.clientPortService.addToCart(this.cartProductSize).subscribe(data => {
        window.location.href = "/shopping-cart";
      }, error => console.log(error))
    } else {
      alert("Please login!")
    }
  }
}
