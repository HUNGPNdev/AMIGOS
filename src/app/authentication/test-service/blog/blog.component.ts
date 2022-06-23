import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Blog } from '../../blog/Blog';
import { BlogsService } from '../../blog/blog.service';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

  constructor(private BlogService:BlogsService,
    private route: ActivatedRoute,
    ) { }
  objBlog :Blog;
  lstBlog:Blog[];
  blogId:number;

  ngOnInit(): void {
    
    this.blogId = this.route.snapshot.params['blogId'];
    this.getAllBlog();
    
  }
  getAllBlog(){
    this.BlogService.getAllBlog(false).subscribe( data => {
      this.lstBlog = data.data;
      if(data.data.length > 0){
        if(this.blogId){
          let dataFilter = data.data.filter(x => x.id == this.blogId);
          this.objBlog =dataFilter.length > 0  ? dataFilter[0] :  new Blog();
        }else{
          this.objBlog = data.data[0];
        }
      }
    }, error => console.log(error))
  }

}
