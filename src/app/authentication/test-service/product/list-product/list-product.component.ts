import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/authentication/entity/product/product';
import { ProductService } from 'src/app/authentication/entity/product/product.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html'
})
export class ListProductComponent implements OnInit {
  src = "./assets/admin/img/new_seo-10-512.png"
  products: Product[];

  constructor(private productService : ProductService) { }

  ngOnInit(): void {
    this.getAllProduct()
  }

  getAllProduct() {
    this.productService.getAllProduct().subscribe( data => {
      this.products = data.data;
    }, error => console.log(error))
  }

  goToUpdate(id: number) {
    // this.router.navigate(['/admin/update-product', id]);

    console.log(id)
  }

  deleteById(id: number) {
    console.log(id)
  }
}
