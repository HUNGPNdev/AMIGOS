import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { CartProductSize } from './cart-product-size';
import { OrderCart } from './user-cart';

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

  getCartByUser(): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/cart-product-size"}`);
  }

  deleteCart(cartId:number): Observable<ResponseApi> {
    return this.httpClient.delete<ResponseApi>(`${this.baseUrl+"/cart-product-size"}/${cartId}`);
  }

  updateCart(cartProductSize:CartProductSize): Observable<ResponseApi> {
    return this.httpClient.put<ResponseApi>(`${this.baseUrl+"/cart-product-size"}`, cartProductSize);
  }

  countCartByUserId(): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/cart-product-size/count"}`);
  }

  getUserDetailByUserName(userName:String): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"/user"}/${userName}`);
  }

  goToOrders(userCart: OrderCart): Observable<ResponseApi> {
    return this.httpClient.post<ResponseApi>(`${this.baseUrl+"/orders"}`, userCart);
  }
}
