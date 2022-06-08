import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/authentication/entity/category/Category';
import { CategoryService } from 'src/app/authentication/entity/category/category.service';
import { Product } from 'src/app/authentication/entity/product/product';
import { ProductService } from 'src/app/authentication/entity/product/product.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html'
})
export class CreateProductComponent implements OnInit {
  src = "./assets/admin/img/new_seo-10-512.png"

  product: Product = new Product();
  categories: Category[];
  selectedImage1: any;
  selectedImage2: any;
  selectedImage3: any;
  form: any = {};

  constructor(private cataService: CategoryService,
    private router: Router,
    private productService: ProductService) { }

  ngOnInit(): void {
    this.getAllCata();
  }

  onSubmit() {
    const uploadData = new FormData();
    uploadData.append('product', JSON.stringify(this.product));
    console.log(uploadData.getAll('product'));

    if(this.selectedImage1 != null) {
      uploadData.append('image_1', this.selectedImage1, this.selectedImage1.name);
    }
    if(this.selectedImage2 != null) {
      uploadData.append('image_2', this.selectedImage2, this.selectedImage2.name);
    }
    if(this.selectedImage3 != null) {
      uploadData.append('image_3', this.selectedImage3, this.selectedImage3.name);
    }
    this.createProduct(uploadData);
    
    
  }

  createProduct(uploadData:FormData) {
    this.productService.createProduct(uploadData).subscribe( data => {
    }, error => console.log(error));
  }

  getAllCata() {
    this.cataService.listCategory().subscribe( data => {
      this.categories = data.data;
    }, error => console.log(error))
  }

  onChangeImage1(event) {
    this.selectedImage1 = event.target.files[0];
  }

  onChangeImage2(event) {
    this.selectedImage2 = event.target.files[0];
  }

  onChangeImage3(event) {
    this.selectedImage3 = event.target.files[0];
  }
}
