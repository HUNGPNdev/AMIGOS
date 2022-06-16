import { Component, OnInit } from '@angular/core';
import { CartProductSize } from '../../entity/client-port/cart-product-size';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { UserCart } from '../../entity/client-port/user-cart';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-cart-order',
  templateUrl: './cart-order.component.html'
})
export class CartOrderComponent implements OnInit {
  cartProductSizes: CartProductSize[];
  totalPrice: number;
  userCart: UserCart = new UserCart();
  
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
      console.log(data)
    }, error => console.log(error))
  }
}
