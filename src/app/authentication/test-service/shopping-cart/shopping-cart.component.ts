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
  constructor(private clientPortService: ClientPortService) { }

  ngOnInit(): void {
    this.getCartByUser();
  }

  getCartByUser() {
    this.clientPortService.getCartByUser().subscribe( data => {
      this.cartProductSizes = data.data;
      console.log(this.cartProductSizes)
      if(this.cartProductSizes.length > 0) {
        this.cartEmpty = true;
      }
    }, error => console.log(error))
  }
}
