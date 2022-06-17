import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResponseApi } from "../ResponseApi";
import { Contact } from "./Contact";

@Injectable({
    providedIn: 'root'
  })
export class ContactService {
    private baseUrl = "http://localhost:8081/contact";

    constructor(private httpClient: HttpClient) { }

    listContact(): Observable<ResponseApi> {     
        return this.httpClient.get<ResponseApi>(`${this.baseUrl}`);
      }
    
      createContact(contact: Contact): Observable<Object> {
        return this.httpClient.post(`${this.baseUrl}`, contact);
      }
    
      getContactById(id: number): Observable<ResponseApi> {
        return this.httpClient.get<ResponseApi>(`${this.baseUrl}/${id}`);
      }
    
      updateById(contact: Contact): Observable<Object> {
        return this.httpClient.patch(`${this.baseUrl}`, contact);
      }
    
      deleteById(id: number): Observable<Object> {
        return this.httpClient.delete(`${this.baseUrl}/${id}`);
      }
}