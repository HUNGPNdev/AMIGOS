import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = "http://localhost:8081/client";

  constructor(private httpClient: HttpClient) { }

  listCategory(limit: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/categories/limit"}/${limit}`);
  }

  getCateById(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/categories"}/${id}`);
  }
}
