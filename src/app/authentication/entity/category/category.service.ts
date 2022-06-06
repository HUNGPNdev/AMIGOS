import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { Category } from './Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = "http://localhost:8081/categories";

  constructor(private httpClient: HttpClient) { }

  listCategory(): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/alls`);
  }

  createCata(category:Category): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}`, category);
  }

  getCateById(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
  }

  updateById(cate: Category): Observable<Object> {
    return this.httpClient.patch(`${this.baseUrl}`, cate);
  }

  deleteById(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
