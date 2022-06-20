import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';

@Injectable({
  providedIn: 'root'
})
export class OrderServiceService {
  private baseUrl = "http://localhost:8081/orders/";

  constructor(private httpClient: HttpClient) { }

  getAllByStatus(status:any): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+"status"}/${status}`);
  }

  orderUpdateStatus(orderId:number,status:any): Observable<ResponseApi>  {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl+orderId+"/status"}/${status}`);
  }

  deleteById(orderId:number): Observable<ResponseApi>  {
    return this.httpClient.delete<ResponseApi>(`${this.baseUrl}${orderId}`);
  }
}
