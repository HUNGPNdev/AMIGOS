import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductSize } from 'src/app/authentication/entity/product-size/product-size';
import { ProductSizeService } from 'src/app/authentication/entity/product-size/product-size.service';
import { Product } from 'src/app/authentication/entity/product/product';
import { ProductService } from 'src/app/authentication/entity/product/product.service';
import { Size } from 'src/app/authentication/entity/size/Size';
import { SizeService } from 'src/app/authentication/entity/size/size.service';

@Component({
  selector: 'app-create-product-size',
  templateUrl: './create-product-size.component.html',
  styleUrls: ['./create-product-size.component.css']
})
export class CreateProductSizeComponent implements OnInit {
  productSize : ProductSize = new ProductSize();
  products : Product[];
  sizes : Size[];
  searchProductText: string;
  status = false;
  id: number;

  constructor(private productService : ProductService,
    private productSizeService : ProductSizeService,
    private sizeService: SizeService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getAllProduct();
    this.listSize();
    if (this.id != null) {
      this.getDetail(this.id);
    }
  }

  onSubmit() {
    if(this.id != null) {
      this.update();
    } else {
      this.addProductSize(this.productSize);
    }
  }

  getAllProduct() {
    this.productService.getAllProduct(false).subscribe( data => {
      this.products = data.data;
    }, error => console.log(error))
  }

  listSize() {
    this.sizeService.listSize().subscribe( data => {
      this.sizes = data.data;
    }, error => console.log(error))
  }

  addProductSize(productSize: ProductSize) {
    this.productSizeService.addProductSize(productSize).subscribe(data => {
      alert("Create Successfully!");
      this.router.navigate(['/list-product-size']);
    }, error => console.log(error));
  }

  getDetail(id: number) {
    this.productSizeService.getDetail(id).subscribe(data => {
      this.productSize = data.data;
      this.id = id;
    })
  }

  update() {
    this.productSizeService.updateById(this.productSize).subscribe( data => {
      alert("Update Successfully!");
      this.router.navigate(['/list-product-size']);
    }, error => console.log(error))
  }
}
