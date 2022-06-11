import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from './authentication/entity/category/Category';
import { CategoryService } from './authentication/entity/category/category.service';
import { TokenStorageService } from './authentication/entity/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  categories: Category[];

  constructor(private cataService: CategoryService,
    private router: Router) { }
 
  ngOnInit() {
    this.getAllCata();
  }

  getAllCata() {
    this.cataService.listCategory().subscribe( data => {
      this.categories = data.data;
    }, error => console.log(error))
  }
}
