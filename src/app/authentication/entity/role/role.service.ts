import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../ResponseApi";

@Injectable({
    providedIn: 'root'
  })
export class RoleService{
    private baseUrl = "http://localhost:8081/role";

    constructor(private httpClient: HttpClient) { }

    listrole(): Observable<ResponseApi> {     
        return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
      }
    
   

}