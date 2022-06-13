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
  
    createContact(contact: Contact): Observable<Object> {
        return this.httpClient.post(`${this.baseUrl}`, contact);
      }
  }