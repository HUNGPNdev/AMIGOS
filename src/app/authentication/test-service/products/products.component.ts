import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { Product } from '../../entity/client-port/product';
import { ProductSize } from '../../entity/client-port/product-size';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  cateId: number;
  products: Product[];
  productLenght: number;
  productSize: ProductSize = new ProductSize();
  productSizes: ProductSize[];
  price = 0;
  

  constructor(private clientPortService: ClientPortService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.cateId = this.route.snapshot.params['cateId'];
    this.getAllProductByCateId();
  }

  getAllProductByCateId() {
    this.clientPortService.getAllProductByCateId(this.cateId).subscribe( data => {
      this.products = data.data;
      this.productLenght = this.products.length;
    }, error => console.log(error))
  }

  findProductSizeByProductId(id: number) {
    this.clientPortService.findProductSizeByProductId(id).subscribe( data => {
      this.productSizes = data.data;
      this.productSize = this.productSizes[0];
      
    }, error => console.log(error))
  }
}
