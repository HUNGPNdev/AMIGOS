import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../entity/ResponseApi";

@Injectable({
    providedIn: 'root'
  })
  export class BlogsService {

    private baseUrl = "http://localhost:8081/blogs";

    constructor(private httpClient: HttpClient) { }
  
 
    getAllBlog(status:any): Observable<ResponseApi>  {
      debugger
      return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/status"}/${status}`);
    }
  
  }