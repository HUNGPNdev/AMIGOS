import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartProductSize } from '../../entity/client-port/cart-product-size';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { Product } from '../../entity/client-port/product';
import { ProductSize } from '../../entity/client-port/product-size';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html'
})
export class DetailsComponent implements OnInit {
  productSize: ProductSize = new ProductSize();
  cartProductSize: CartProductSize = new CartProductSize();
  productSizeModel: ProductSize = new ProductSize();
  productSizes: ProductSize[];
  productSizeModels: ProductSize[];
  productId: number;
  quantity = 1;
  quantityPopup = 1;
  products: Product[];
  message = false;
  messageModel = false;

  constructor(private clientPortService: ClientPortService,
    private route: ActivatedRoute,
    private router: Router,
    private token: TokenStorageService) { }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    this.findProductSizeByProductId();
  }

  findProductSizeByProductId() {
    this.clientPortService.findProductSizeByProductId(this.productId).subscribe(data => {
      this.productSizes = data.data;
      this.productSize = this.productSizes[0];
      console.log(this.productSizes)
      console.log(this.productSize)
      this.getProductRelatedItem(this.productSize.cateId, this.productId);
    }, error => console.log(error))
  }

  findProductSizeBySizeName(id: number) {
    for (var i = 0; i <= this.productSizes.length; i++) {
      if (this.productSizes[i].id == id) {
        this.productSize = this.productSizes[i];
        this.message = false;
        break;
      }
    }
  }

  getProductRelatedItem(cateId: number, productId: number) {
    var limit = 4;
    this.clientPortService.getProductRelatedItem(limit, cateId, productId).subscribe(data => {
      this.products = data.data;
    }, error => console.log(error))
  }

  findProductSizeByProductIdModel(id: number) {
    this.clientPortService.findProductSizeByProductId(id).subscribe(data => {
      this.productSizeModels = data.data;
      this.productSizeModel = this.productSizeModels[0];
    }, error => console.log(error))
  }

  findProductSizeBySizeNameModel(id: number) {
    for (var i = 0; i <= this.productSizeModels.length; i++) {
      if (this.productSizeModels[i].id == id) {
        this.productSizeModel = this.productSizeModels[i];
        this.message = false;
        break;
      }
    }
  }

  addToCart(productSizeId: number) {
    if (this.token.getUsername()) {
      this.cartProductSize.count = this.quantity;
      this.cartProductSize.productSizeId = productSizeId;
      this.clientPortService.addToCart(this.cartProductSize).subscribe(data => {
        window.location.href = "/shopping-cart";
      }, error => console.log(error))
    } else {
      alert("Please login!")
    }
  }

  onChangeSort(event) {
    var value = event.target.value;
    if(value > this.productSize.count) {
      this.message = true;
    } else {
      this.message = false;
      this.quantity = value;
    }
  }

  onChangeSortPopup(event) {
    var value = event.target.value;
    if(value > this.productSizeModel.count) {
      this.messageModel = true;
    } else {
      this.messageModel = false;
      this.quantity = value;
    }
  }

  addToCartPopup() {
    if (this.token.getUsername()) {
      this.cartProductSize.count = this.quantity;
      this.cartProductSize.productSizeId = this.productSizeModel.id;
      this.clientPortService.addToCart(this.cartProductSize).subscribe(data => {
        window.location.href = "/shopping-cart";
      }, error => console.log(error))
    } else {
      alert("Please login!")
    }
  }
}
