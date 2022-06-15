import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { CartProductSize } from './cart-product-size';

@Injectable({
  providedIn: 'root'
})
export class ClientPortService {
  private baseUrl = "http://localhost:8081/client";

  constructor(private httpClient: HttpClient) { }

  getAllProductByCateId(cateId: any): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/products"}/${cateId}`);
  }

  findProductSizeByProductId(productId: any): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/products-size"}/${productId}`);
  }

  getProductNewReleases(limit: any): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/products-size/limit"}/${limit}`);
  }

  getProductRelatedItem(limit: any, cateId: number, productId: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/products/"+productId+"/cateId/"+cateId+"/limit/"+limit}`);
  }

  getProductFeaturedProducts(limit: any): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/products/featured/limit"}/${limit}`);
  }

  addToCart(cartProductSize:CartProductSize): Observable<ResponseApi> {
    return this.httpClient.post<ResponseApi>(`${this.baseUrl+"/cart-product-size/add-to-cart"}`, cartProductSize);
  }
}
