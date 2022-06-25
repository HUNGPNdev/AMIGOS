import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private baseUrl = "http://localhost:8081/reports";

  constructor(private httpClient: HttpClient) { }

  reportPrint(): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
  }

  countUser(): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/users"}`);
  }

  countComment(): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/comments"}`);
  }

  countProduct(): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/products"}`);
  }

  countCategory(): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/categories"}`);
  }
}
