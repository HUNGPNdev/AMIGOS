import { Component, OnInit } from '@angular/core';
import { CartProductSize } from '../../entity/client-port/cart-product-size';
import { ClientPortService } from '../../entity/client-port/client-port.service'; import { OrderCart } from '../../entity/client-port/user-cart';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-cart-order',
  templateUrl: './cart-order.component.html'
})
export class CartOrderComponent implements OnInit {
  cartProductSizes: CartProductSize[];
  totalPrice: number;
  userCart: OrderCart = new OrderCart();

  constructor(private clientPortService: ClientPortService,
    private token: TokenStorageService) { }

  ngOnInit(): void {
    this.getCartByUser();
    this.getUserDetailByUserName();

  }

  getCartByUser() {
    this.clientPortService.getCartByUser().subscribe(data => {
      this.cartProductSizes = data.data;
      this.totalPrice = Number.parseFloat(data.message);
    }, error => console.log(error))
  }

  getUserDetailByUserName() {
    var userName = this.token.getUsername();
    this.clientPortService.getUserDetailByUserName(userName).subscribe(data => {
      this.userCart = data.data;
    }, error => console.log(error))
  }

  goToOrders() {
    console.log("goToOrders")
    if (this.token.getUsername()) {
      this.clientPortService.goToOrders(this.userCart).subscribe(data => {
        window.location.href = "/shopping-cart";
      }, error => console.log(error))
    } else {
      alert("Please login!")
    }
  }
}
