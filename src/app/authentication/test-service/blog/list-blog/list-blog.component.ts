import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Blog } from 'src/app/authentication/entity/blog/Blog';
import { BlogsService } from 'src/app/authentication/entity/blog/blog.service';

@Component({
  selector: 'app-list-blog',
  templateUrl: './list-blog.component.html',
  styleUrls: ['./list-blog.component.css']
})
export class ListBlogComponent implements OnInit {
  searchText: string;
  Blogs: Blog[];
  p:number;
  status = false;

  constructor(private BlogService : BlogsService,
    private router: Router) { }

  ngOnInit(): void {
  }


  getAllBlog() {
    this.BlogService.getAllBlog(this.status).subscribe( data => {
     
      this.Blogs = data.data;
    }, error => console.log(error))
  }

  goToUpdate(id: number) {
    this.router.navigate(['/update-blog', id]);
  }

  deleteById(id: number) {
    this.BlogService.deleteById(id).subscribe( data => {
      this.getAllBlog();
    }, error => console.log(error))
  }

  findByStatus() {
    this.status = !this.status;
    this.BlogService.getAllBlog(this.status).subscribe( data => {
      this.Blogs = data.data;
    }, error => console.log(error))
  }
}
