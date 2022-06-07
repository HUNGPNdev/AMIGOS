import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html'
})
export class CreateProductComponent implements OnInit {
  src = "./assets/admin/img/new_seo-10-512.png"

  constructor() { }

  ngOnInit(): void {
  }

}
