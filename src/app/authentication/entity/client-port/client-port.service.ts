import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';

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

}
