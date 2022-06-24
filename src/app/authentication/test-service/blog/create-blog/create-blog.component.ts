import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Blog } from 'src/app/authentication/entity/blog/Blog';
import { BlogsService } from 'src/app/authentication/entity/blog/blog.service';

@Component({
  selector: 'app-create-blog',
  templateUrl: './create-blog.component.html',
  styleUrls: ['./create-blog.component.css']
})
export class CreateBlogComponent implements OnInit {
  src = "./assets/admin/img/new_seo-10-512.png"
  image = this.src;
  selectedImage: any;
  id:number = 0;
  Blog: Blog = new Blog();
  form: any = {};
  constructor( private blogService: BlogsService, private router: Router,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if(this.id){
      this.getBlogById(this.id);
    }
    
  }
  onSubmit() {
    const uploadData = new FormData();
    this.Blog.id = this.id;
    uploadData.append('blog', JSON.stringify(this.Blog));
    // console.log(uploadData.getAll('product'));

    if (this.selectedImage != null) {
      uploadData.append('image', this.selectedImage, this.selectedImage.name);
    }
    
    if (this.id) {
      this.updateProduct(uploadData);
    } else {
      this.createProduct(uploadData);
    }


  }



  createProduct(uploadData: FormData) {
    this.blogService.createBlog(uploadData).subscribe(data => {
      alert("Save Successfully!");
      this.router.navigate(['/list-blog']);
    }, error => console.log(error));
  }

  updateProduct(uploadData: FormData) {
    this.blogService.updateBlog(uploadData).subscribe(data => {
      alert("Updated Successfully!");
    }, error => console.log(error));
  }
  onChangeImage(event) {
   
    var file = event.target.files[0];
    if (file.size < 1048576) {
      this.selectedImage = file;
    }
  }
  getBlogById(id: number) {
    this.blogService.getBlogById(id).subscribe(data => {
      this.Blog = data.data;
      this.image = "http://localhost:8081/images/" + this.Blog.image;
      
      this.id = id;
    })
  }
  

}

