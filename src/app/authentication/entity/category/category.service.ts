import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseApi } from '../ResponseApi';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = "http://localhost:8081/categories";

  constructor(private httpClient: HttpClient) { }

  listCategory(): Observable<ResponseApi> {
    return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
  }
}