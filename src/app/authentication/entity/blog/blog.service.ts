import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../ResponseApi";

@Injectable({
    providedIn: 'root'
  })
  export class BlogsService {

    private baseUrl = "http://localhost:8081/blogs";

    constructor(private httpClient: HttpClient) { }
  
    createBlog(file: any): Observable<Object> {
      return this.httpClient.post(`${this.baseUrl}`, file);
    }
  
    getAllBlog(status:any): Observable<ResponseApi>  {
      return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/status"}/${status}`);
    } 
  
    updateBlog(file: any): Observable<Object> {
      return this.httpClient.patch(`${this.baseUrl}`, file);
    }
  
    getBlogById(id: number): Observable<ResponseApi> {
      return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
    }
  
    deleteById(id: number): Observable<Object> {
      return this.httpClient.delete(`${this.baseUrl}/${id}`);
    }
  }