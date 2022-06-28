import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../entity/report/report.service';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private baseUrl = "http://localhost:8081/contact";
  userCount = 0;
  cateCount = 0;
  productCount = 0;
  commentCount = 0;
  
  info: any;
 
  constructor(private token: TokenStorageService,
    private reportService: ReportService) { }
 
  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };

    this.countUser();
    this.countComment();
    this.countProduct();
    this.countCategory();
  }
 
  logout() {
    this.token.signOut();
    window.location.reload();
  }

  reportPrint() {
    this.reportService.reportPrint().subscribe(data => {
    }, error => console.log(error))
  }

  countUser() {
    this.reportService.countUser().subscribe(data => {
      this.userCount = data.data;
    }, error => console.log(error))
  }

  countComment() {
    this.reportService.countComment().subscribe(data => {
      this.commentCount = data.data;
    }, error => console.log(error))
  }

  countProduct() {
    this.reportService.countProduct().subscribe(data => {
      this.productCount = data.data;
    }, error => console.log(error))
  }

  countCategory() {
    this.reportService.countCategory().subscribe(data => {
      this.cateCount = data.data;
    }, error => console.log(error))
  }
}
