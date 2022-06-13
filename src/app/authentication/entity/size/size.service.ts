import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';
import { Size } from './Size';

@Injectable({
  providedIn: 'root'
})
export class SizeService {
  private baseUrl = "http://localhost:8081/sizes";

  constructor(private httpClient: HttpClient) { }

  listSize(): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
  }

  addSize(size:Size): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}`, size);
  }

  getSizeById(id: number): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
  }

  updateById(size:Size): Observable<Object> {
    return this.httpClient.patch(`${this.baseUrl}`, size);
  }
}
