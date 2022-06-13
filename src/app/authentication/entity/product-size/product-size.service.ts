import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { ProductSize } from './product-size';

@Injectable({
  providedIn: 'root'
})
export class ProductSizeService {
  private baseUrl = "http://localhost:8081/product-size";

  constructor(private httpClient: HttpClient) { }

  addProductSize(file: any): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}`, file);
  }

  getAllByStatus(status:any): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/status"}/${status}`);
  }

  getDetail(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
  }

  updateProduct(file: any): Observable<Object> {
    return this.httpClient.patch(`${this.baseUrl}`, file);
  }

  updateById(productSize: ProductSize): Observable<Object> {
    return this.httpClient.patch(`${this.baseUrl}`, productSize);
  }

  deleteById(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
