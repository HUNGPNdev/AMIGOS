import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductSize } from 'src/app/authentication/entity/product-size/product-size';
import { ProductSizeService } from 'src/app/authentication/entity/product-size/product-size.service';

@Component({
  selector: 'app-list-product-size',
  templateUrl: './list-product-size.component.html'
})
export class ListProductSizeComponent implements OnInit {
  productSize: ProductSize[];
  searchText: string;
  p:number;
  status = false;

  constructor(private productSizeService : ProductSizeService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllByStatus();
  }

  goToUpdate(id:number) {
    this.router.navigate(['/update-product-size', id]);
  }

  deleteById(id:number) {
    this.productSizeService.deleteById(id).subscribe( data => {
      this.getAllByStatus();
    }, error => console.log(error))
  }

  findByStatus() {
    this.status = !this.status;
    this.productSizeService.getAllByStatus(this.status).subscribe( data => {
      this.productSize = data.data;
    }, error => console.log(error))
  }

  getAllByStatus() {
    this.productSizeService.getAllByStatus(this.status).subscribe( data => {
      this.productSize = data.data;
      console.log(this.productSize);
    }, error => console.log(error))
  }
}
