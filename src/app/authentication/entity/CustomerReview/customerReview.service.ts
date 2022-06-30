import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { CustomerReview } from './CustomerReviewEntity';

@Injectable({
  providedIn: 'root'
})
export class CustomerReviewService {

  private baseUrl = "http://localhost:8081/customerreview";

  constructor(private httpClient: HttpClient) { }

  
  AddCustomerReview(customerReview:CustomerReview): Observable<object> {
    return this.httpClient.post<object>(`${this.baseUrl}`,customerReview);
  }
  getCustomerReviewByProId(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
  }
  checkCustomerReviewByProId(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/checkCustomerReview/${id}`);
  }
}
