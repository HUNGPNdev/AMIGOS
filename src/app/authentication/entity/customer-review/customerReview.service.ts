import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../ResponseApi";

@Injectable({
    providedIn: 'root'
  })
  export class CustomerReviewService {

    private baseUrl = "http://localhost:8081/customerreview";

    constructor(private httpClient: HttpClient) { }
  
  
    getAll(): Observable<ResponseApi>  {
      return this.httpClient.get<ResponseApi>(`${this.baseUrl}/getAll`);
    } 
  
    deleteById(id: number): Observable<Object> {
      return this.httpClient.delete(`${this.baseUrl}/${id}`);
    }
  }