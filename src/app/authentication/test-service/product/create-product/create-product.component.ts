import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  image1 = this.src;
  image2 = this.src;
  image3 = this.src;

  product: Product = new Product();
  categories: Category[];
  selectedImage1: any;
  selectedImage2: any;
  selectedImage3: any;
  form: any = {};
  id: number = 0;

  constructor(private cataService: CategoryService,
    private router: Router,
    private productService: ProductService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAllCata();
    this.id = this.route.snapshot.params['id'];
    if (this.id != null) {
      this.getCateById(this.id);
    }
  }

  onSubmit() {
    const uploadData = new FormData();
    this.product.id = this.id;
    uploadData.append('product', JSON.stringify(this.product));
    // console.log(uploadData.getAll('product'));

    if (this.selectedImage1 != null) {
      uploadData.append('image_1', this.selectedImage1, this.selectedImage1.name);
    }
    if (this.selectedImage2 != null) {
      uploadData.append('image_2', this.selectedImage2, this.selectedImage2.name);
    }
    if (this.selectedImage3 != null) {
      uploadData.append('image_3', this.selectedImage3, this.selectedImage3.name);
    }

    if (this.id != null) {
      this.updateProduct(uploadData);
    } else {
      this.createProduct(uploadData);
    }


  }

  createProduct(uploadData: FormData) {
    this.productService.createProduct(uploadData).subscribe(data => {
      alert("Create Successfully!");
      this.router.navigate(['/list-product']);
    }, error => console.log(error));
  }

  updateProduct(uploadData: FormData) {
    this.productService.updateProduct(uploadData).subscribe(data => {
      alert("Updated Successfully!");
    }, error => console.log(error));
  }

  getAllCata() {
    this.cataService.listCategory().subscribe(data => {
      this.categories = data.data;
    }, error => console.log(error))
  }

  onChangeImage1(event) {
    var file = event.target.files[0];
    if (file.size < 1048576) {
      this.selectedImage1 = file;
    }
  }

  onChangeImage2(event) {
    var file = event.target.files[0];
    if (file.size < 1048576) {
      this.selectedImage2 = file;
    }
  }

  onChangeImage3(event) {
    var file = event.target.files[0];
    if (file.size < 1048576) {
      this.selectedImage3 = file;
    }
  }

  getCateById(id: number) {
    this.productService.getProductById(id).subscribe(data => {
      this.product = data.data;
      this.image1 = "http://localhost:8081/images/" + this.product.image_1;
      this.image2 = "http://localhost:8081/images/" + this.product.image_2;
      this.image3 = "http://localhost:8081/images/" + this.product.image_3;
      this.id = id;
    })
  }
}
