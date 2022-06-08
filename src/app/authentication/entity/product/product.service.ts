import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
}
