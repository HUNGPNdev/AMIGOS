import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../../entity/category/Category';
import { CategoryService } from '../../entity/category/category.service';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { Product } from '../../entity/client-port/product';
import { ProductSize } from '../../entity/client-port/product-size';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html'
})
export class ProductsComponent implements OnInit {
  cateId: number;
  cateName: String;
  products: Product[];
  productLenght: number;
  productSize: ProductSize = new ProductSize();
  productSizes: ProductSize[];
  productNewReleases: Product[];
  category: Category = new Category();
  searchText: string;
  p:number;

  constructor(private clientPortService: ClientPortService,
    private cataService: CategoryService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.cateId = this.route.snapshot.params['cateId'];
    this.getCateById();
    this.getAllProductByCateId();
    this.getProductNewReleases();
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

  findProductSizeBySizeName(sizeName: String) {
    for(var i=0; i<= this.productSizes.length; i++) {
      if(this.productSizes[i].sizeName == sizeName) {
        this.productSize = this.productSizes[i];
        break;
      }
    }
  }

  getCateById() {
    this.cataService.getCateById(this.cateId).subscribe( data => {
      this.category = data.data;
    })
  }

  getProductNewReleases() {
    var limit = 5;
    this.clientPortService.getProductNewReleases(limit).subscribe( data => {
      this.productNewReleases = data.data;
    }, error => console.log(error))
  }
}
