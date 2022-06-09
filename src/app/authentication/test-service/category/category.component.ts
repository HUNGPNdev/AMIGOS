import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from '../../entity/category/Category';
import { CategoryService } from '../../entity/category/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  category: Category = new Category();
  categories: Category[];
  searchText: string;
  p:number;
  id: number;

  constructor(private cataService: CategoryService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllCata();
  }

  onSubmit() {
    if(this.id != 0) {
      this.category.id = this.id;
      this.updateById();
    } else {
      this.createCategory();
    }
  }

  getAllCata() {
    this.cataService.listCategory().subscribe( data => {
      this.categories = data.data;
    }, error => console.log(error))
  }

  createCategory() {
    this.cataService.createCata(this.category).subscribe( data => {
      this.category = new Category();
     
      this.getAllCata();
    }, error => console.log(error));
  }

  getCateById(id: number) {
    this.cataService.getCateById(id).subscribe( data => {
      this.category = data.data;
      this.id = id;
    })
  }

  updateById() {
    this.cataService.updateById(this.category).subscribe( data => {
      this.id = 0;
      this.category = new Category();
      this.getAllCata();
    }, error => console.log(error))
  }

  deleteById(id: number) {
    this.cataService.deleteById(id).subscribe( data => {
      this.getAllCata();
    }, error => console.log(error))
  }
}
