import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartProductSize } from '../../entity/client-port/cart-product-size';

import { ClientPortService } from '../../entity/client-port/client-port.service';
import { Product } from '../../entity/client-port/product';
import { ProductSize } from '../../entity/client-port/product-size';
import { CustomerReviewService } from '../../entity/CustomerReview/customerReview.service';
import { CustomerReview } from '../../entity/CustomerReview/CustomerReviewEntity';
import { cutomerReviewCountStar } from '../../entity/CustomerReview/cutomerReviewCountStar';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html'
})
export class DetailsComponent implements OnInit {
  productSize: ProductSize = new ProductSize();
  cartProductSize: CartProductSize = new CartProductSize();
  productSizeModel: ProductSize = new ProductSize();
  productSizes: ProductSize[];
  productSizeModels: ProductSize[];
  productId: number;
  quantity = 1;
  quantityPopup = 1;
  products: Product[];
/**customer review */
  elementWriteComment = false;
  txtwritecontent = "Write content";
  SttStart:number =0; 
  formCustomerReview: any = {};
  customerReviewEntity : CustomerReview;
  listCustomerReviewByProId : CustomerReview[];
 
  customerReviewCountStar : cutomerReviewCountStar = new cutomerReviewCountStar() ;
  countTotalStar:number = 0;
  avgStart:number;
  /**end customer review */

  constructor(private clientPortService: ClientPortService,
    private route: ActivatedRoute,
    private token: TokenStorageService,
    private customerReviewService : CustomerReviewService
    ) { }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    this.findProductSizeByProductId();
    this.LoadAllCustomerReview();
  }

  findProductSizeByProductId() {
    this.clientPortService.findProductSizeByProductId(this.productId).subscribe(data => {
      this.productSizes = data.data;
      this.productSize = this.productSizes[0];
      this.getProductRelatedItem(this.productSize.cateId, this.productId);
    }, error => console.log(error))
  }

  findProductSizeBySizeName(sizeName: String) {
    for (var i = 0; i <= this.productSizes.length; i++) {
      if (this.productSizes[i].sizeName == sizeName) {
        this.productSize = this.productSizes[i];
        break;
      }
    }
  }

  getProductRelatedItem(cateId: number, productId: number) {
    var limit = 4;
    this.clientPortService.getProductRelatedItem(limit, cateId, productId).subscribe(data => {
      this.products = data.data;
    }, error => console.log(error))
  }

  findProductSizeByProductIdModel(id: number) {
    this.clientPortService.findProductSizeByProductId(id).subscribe(data => {
      this.productSizeModels = data.data;
      this.productSizeModel = this.productSizeModels[0];
    }, error => console.log(error))
  }

  findProductSizeBySizeNameModel(sizeName: String) {
    for (var i = 0; i <= this.productSizeModels.length; i++) {
      if (this.productSizeModels[i].sizeName == sizeName) {
        this.productSizeModel = this.productSizeModels[i];
        break;
      }
    }
  }

  addToCart(productSizeId: number) {
    if (this.token.getUsername()) {
      this.cartProductSize.count = this.quantity;
      this.cartProductSize.productSizeId = productSizeId;
      this.clientPortService.addToCart(this.cartProductSize).subscribe(data => {
        window.location.href = "/shopping-cart";
      }, error => console.log(error))
    } else {
      alert("Please login!")
    }
  }

  onChangeSort(event) {
    var value = event.target.value;
    this.quantity = value;
  }



/** Customer review  */
  WriteReview(){
    if(this.token.getUsername()) {
      if(this.elementWriteComment){
        this.elementWriteComment = false;
      }else{
        this.elementWriteComment = true;
      }
      }else
        alert("Please login!")
  }
  ClickStart(starId:number){
    if(starId ==  this.SttStart ){
      this.SttStart = starId -1;
    }else{
      this.SttStart = starId;
    }
  }
  LoadAllCustomerReview(){
    this.customerReviewService.getCustomerReviewByProId(this.productId).subscribe(data=>{
      if(data.code == 200){
        this.listCustomerReviewByProId =  data.data.listCustomerReview;
        this.countTotalStar =  data.data.listCustomerReview.length;
        // let starCount = 0;
        // let activeStar = 0;
        // let countactiveStar = 0;
       for (let value  of Object.values(data.data.coutStarts)) {
          let object :any = value;
          if(object[0] == 1){
            // activeStar +=1;
            // countactiveStar++;
            // starCount += (1 *  object.length);
            this.customerReviewCountStar.countStar1 = object.length;
            this.customerReviewCountStar.widthcountStar1 =(object.length /this.countTotalStar) * 100;
          }else if(object[0] == 2){
            // activeStar +=2;
            // starCount += (2 *  object.length);
            // countactiveStar++;
            this.customerReviewCountStar.countStar2 = object.length;
            this.customerReviewCountStar.widthcountStar2 =(object.length /this.countTotalStar) * 100;
          }else if(object[0] == 3){
            // activeStar +=3;
            // starCount += (3 *  object.length);
            // countactiveStar++;
            this.customerReviewCountStar.countStar3 = object.length;
            this.customerReviewCountStar.widthcountStar3 = (object.length /this.countTotalStar) * 100;
          }else if(object[0] == 4){
            // activeStar +=4;
            // starCount += (4 *  object.length);
            // countactiveStar++;
            this.customerReviewCountStar.countStar4 = object.length;
            this.customerReviewCountStar.widthcountStar4 = (object.length /this.countTotalStar) * 100;
          }else if(object[0] == 5){
            // activeStar +=5;
            // starCount += (5 *  object.length);
            // countactiveStar++;
            this.customerReviewCountStar.countStar5 = object.length;
            this.customerReviewCountStar.widthcountStar5 = (object.length /this.countTotalStar) * 100;
          }
        }

       this.avgStart = data.data.avgStar;

      }
    })
  }
  onSubmitCustomerRV(){
    // let a = this.formCustomerReview ;
    // this.formCustomerReview.rating = this.SttStart;
    this.customerReviewEntity = new CustomerReview(
      this.formCustomerReview.title,
      this.formCustomerReview.comment,
      this.SttStart,
      this.productId
    );
   
    this.customerReviewService.AddCustomerReview(this.customerReviewEntity).subscribe((data:any)=>{
      if(data.code == 200){
        this.LoadAllCustomerReview();
      }
    })
  }
  styleWith(width:number){
console.log(width);
    return'50'
    
  }

  /**end */

  onChangeSortPopup(event) {
    var value = event.target.value;
    this.quantityPopup = value;
  }

  addToCartPopup() {
    if (this.token.getUsername()) {
      this.cartProductSize.count = this.quantity;
      this.cartProductSize.productSizeId = this.productSizeModel.id;
      this.clientPortService.addToCart(this.cartProductSize).subscribe(data => {
        window.location.href = "/shopping-cart";
      }, error => console.log(error))
    } else {
      alert("Please login!")
    }
  }

}
