import { Component, OnInit } from '@angular/core';
import { Size } from '../../entity/size/Size';
import { SizeService } from '../../entity/size/size.service';

@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css']
})
export class SizeComponent implements OnInit {
  size: Size = new Size();
  sizes: Size[];
  searchText: string;
  p:number;
  id: number = 0;

  constructor(private sizeService: SizeService) { }

  ngOnInit(): void {
    this.listSize();
  }

  onSubmit() {
    if(this.id != 0) {
      this.updateById();
    } else {
      this.createCategory();
    }
  }
  getSizeById(id:number) {
    this.sizeService.getSizeById(id).subscribe( data => {
      this.size = data.data;
      this.id = id;
    }, error => console.log(error))
  }
  
  listSize() {
    this.sizeService.listSize().subscribe( data => {
      this.sizes = data.data;
    }, error => console.log(error))
  }

  createCategory() {
    this.sizeService.addSize(this.size).subscribe( data => {
      this.size = new Size();
      this.listSize();
    }, error => console.log(error));
  }
  
  updateById() {
    this.sizeService.updateById(this.size).subscribe( data => {
      this.id = 0;
      this.size = new Size();
      this.listSize();
    }, error => console.log(error))
  }
}
