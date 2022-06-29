import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../ResponseApi";
import { User } from "./User";

@Injectable({
    providedIn: 'root'
  })
export class UserService {
    private baseUrl = "http://localhost:8081/user";

    constructor(private httpClient: HttpClient) { }

    listUser(): Observable<ResponseApi> {     
        return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
      }
    
      createUser(user: User): Observable<Object> {
        return this.httpClient.post(`${this.baseUrl}`, user);
      }
    
      getUserById(id: number): Observable<ResponseApi> {
        return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
      }
    
      updateById(user: User): Observable<Object> {
        return this.httpClient.patch(`${this.baseUrl}`, user);
      }
    
      deleteById(id: number): Observable<Object> {
        return this.httpClient.delete(`${this.baseUrl}/${id}`);
      }
}