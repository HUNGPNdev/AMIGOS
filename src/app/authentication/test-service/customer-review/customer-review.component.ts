import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerReviewService } from '../../entity/customer-review/customerReview.service';
import { CustomerReview } from '../../entity/customer-review/CustomerReviewEntity';

@Component({
  selector: 'app-customer-review',
  templateUrl: './customer-review.component.html',
  styleUrls: ['./customer-review.component.css']
})
export class CustomerReviewComponent implements OnInit {
  customerReviews : CustomerReview[];
  constructor(
    private  customerReviewService :CustomerReviewService ,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getAll();
  }
  getAll(){
    this.customerReviewService.getAll().subscribe( data => {
      this.customerReviews = data.data;
    }, error => console.log(error))
  }
  deleteById(id:number){
    debugger;
  
    this.customerReviewService.deleteById(id).subscribe(data =>{
      this.getAll();
    })
  }
}
