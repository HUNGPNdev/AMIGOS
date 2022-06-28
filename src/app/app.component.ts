import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from './authentication/entity/category/Category';
import { CategoryService } from './authentication/entity/category/category.service';
import { ClientPortService } from './authentication/entity/client-port/client-port.service';
import { TokenStorageService } from './authentication/entity/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  categories: Category[];
  count: number;
  constructor(private cataService: CategoryService,
    private clientPortService: ClientPortService,
    private router: Router,
    private tokenStorage: TokenStorageService) { }
 
  ngOnInit() {
    this.getAllCata();
    this.countCartByUserId();
  }

  getAllCata() {
    var limit = 5;
    this.cataService.listCategory(limit).subscribe( data => {
      this.categories = data.data;
    }, error => console.log(error))
  }

  countCartByUserId() {
    if (this.tokenStorage.getToken()) {
      this.clientPortService.countCartByUserId().subscribe( data => {
        this.count = data.data;
      }, error => console.log(error))
    }
  }
}
