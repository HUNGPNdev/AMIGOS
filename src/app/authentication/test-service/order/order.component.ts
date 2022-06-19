import { Component, OnInit } from '@angular/core';
import { CartProductSize } from '../../entity/cart/cart-product-size';
import { OrderServiceService } from '../../entity/cart/order-service.service';
import { Orders } from '../../entity/cart/Orders';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit {
  orders: Orders[];
  orderDetail: Orders = new Orders();
  status = false;
  cartProductSizes: CartProductSize[];
  orderStatus: String;

  constructor(private orderService: OrderServiceService) { }

  ngOnInit(): void {
    this.getAllByStatus();
  }

  getAllByStatus() {
    this.orderService.getAllByStatus(this.status).subscribe(data => {
      this.orders = data.data;
      console.log(this.orders)
    }, error => console.log(error))
  }

  findByStatus() {
    this.status = !this.status;
    this.orderService.getAllByStatus(this.status).subscribe(data => {
      this.orders = data.data;
    }, error => console.log(error))
  }

  viewOrders(order: any) {
    this.orderDetail = order;
    console.log(this.orderDetail)
  }

  deleteById() {
    console.log(this.orderDetail)
  }

  orderUpdateStatus(id:number) {
    console.log(id)
    console.log(this.orderStatus)
  }
}
