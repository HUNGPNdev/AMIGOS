import { Component, OnInit } from '@angular/core';
import { CartProductSize } from '../../entity/cart/cart-product-size';
import { OrderServiceService } from '../../entity/cart/order-service.service';
import { Orders } from '../../entity/cart/Orders';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orders: Orders[];
  orderDetail: Orders = new Orders();
  status = false;
  cartProductSizes: CartProductSize[];
  orderStatus: String;
  p:number;
  searchText: string;

  constructor(private orderService: OrderServiceService) { }

  ngOnInit(): void {
    this.getAllByStatus();
  }

  getAllByStatus() {
    this.orderService.getAllByStatus(this.status).subscribe(data => {
      this.orders = data.data;
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
  }

  deleteById(id:number) {
    this.orderService.deleteById(id).subscribe(data => {
      this.getAllByStatus();
    }, error => console.log(error))
  }

  orderUpdateStatus(id:number) {
    this.orderService.orderUpdateStatus(id, this.orderStatus).subscribe(data => {
      this.orderDetail = data.data;
      this.getAllByStatus();
    }, error => console.log(error))
  }
}
