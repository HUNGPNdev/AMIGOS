import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/authentication/entity/product/product';
import { ProductService } from 'src/app/authentication/entity/product/product.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html'
})
export class ListProductComponent implements OnInit {
  src = "./assets/admin/img/new_seo-10-512.png";
  products: Product[];
  searchText: string;
  p:number;
  status = false;

  constructor(private productService : ProductService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllProduct()
  }

  getAllProduct() {
    this.productService.getAllProduct(this.status).subscribe( data => {
      this.products = data.data;
    }, error => console.log(error))
  }

  goToUpdate(id: number) {
    this.router.navigate(['/update-product', id]);
  }

  deleteById(id: number) {
    this.productService.deleteById(id).subscribe( data => {
      this.getAllProduct();
    }, error => console.log(error))
  }

  findByStatus() {
    this.status = !this.status;
    this.productService.getAllProduct(this.status).subscribe( data => {
      this.products = data.data;
    }, error => console.log(error))
  }
}
