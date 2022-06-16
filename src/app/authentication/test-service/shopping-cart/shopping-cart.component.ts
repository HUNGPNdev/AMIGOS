import { Component, OnInit } from '@angular/core';
import { CartProductSize } from '../../entity/client-port/cart-product-size';
import { ClientPortService } from '../../entity/client-port/client-port.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  cartProductSize: CartProductSize = new CartProductSize();
  cartProductSizes: CartProductSize[];
  cartEmpty = false;
  totalPrice: number;

  constructor(private clientPortService: ClientPortService) { }

  ngOnInit(): void {
    this.getCartByUser();
  }

  getCartByUser() {
    this.clientPortService.getCartByUser().subscribe(data => {
      this.cartProductSizes = data.data;
      if (this.cartProductSizes.length > 0) {
        this.cartEmpty = true;
      }
      this.totalPrice = Number.parseFloat(data.message);
    }, error => console.log(error))
  }

  deleteCart(cartId: number) {
    this.clientPortService.deleteCart(cartId).subscribe(data => {
      this.getCartByUser();
    }, error => console.log(error))
  }

  updateCount(event, cartId: number) {
    var value = event.target.value;
    for (var i = 0; i <= this.cartProductSizes.length; i++) {
      if (this.cartProductSizes[i].id == cartId) {
        this.cartProductSizes[i].count = value;
      }
    }
  }

  updateCart(cartId: number) {
    for (var i = 0; i <= this.cartProductSizes.length; i++) {
      if (this.cartProductSizes[i].id == cartId) {
        this.clientPortService.updateCart(this.cartProductSizes[i]).subscribe(data => {
          this.getCartByUser();
        }, error => console.log(error))
      }
    }
  }

}
