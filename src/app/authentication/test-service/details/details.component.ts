import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { ProductSize } from '../../entity/client-port/product-size';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  productSize: ProductSize = new ProductSize();
  productSizes: ProductSize[];
  productId:number;

  constructor(private clientPortService: ClientPortService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    this.findProductSizeByProductId();
  }

  findProductSizeByProductId() {
    this.clientPortService.findProductSizeByProductId(this.productId).subscribe( data => {
      this.productSizes = data.data;
      this.productSize = this.productSizes[0];
      console.log(this.productSizes);
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
}
