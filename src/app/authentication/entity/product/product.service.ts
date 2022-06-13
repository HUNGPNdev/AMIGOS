import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = "http://localhost:8081/products";

  constructor(private httpClient: HttpClient) { }

  createProduct(file: any): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}`, file);
  }

  getAllProduct(status:any): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/status"}/${status}`);
  } 

  updateProduct(file: any): Observable<Object> {
    return this.httpClient.patch(`${this.baseUrl}`, file);
  }

  getProductById(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
  }

  deleteById(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
