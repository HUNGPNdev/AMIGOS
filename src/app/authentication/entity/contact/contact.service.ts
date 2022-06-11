import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../ResponseApi";

@Injectable({
    providedIn: 'root'
  })
  export class ContactService {
  
    private baseUrl = "http://localhost:8081/contact";
  
    constructor(private httpClient: HttpClient) { }
  
    listCategory(): Observable<ResponseApi> {
      return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
    }
  }