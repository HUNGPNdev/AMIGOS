import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../entity/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private Url = 'http://localhost:8081/api/test/';
 
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { }
 
  // getUserBoard(): Observable<string> {

  //   let httpOptions = new HttpHeaders()
  //     .set('Authorization', 'Bearer ' + this.tokenStorage.getToken());
  //   console.log(httpOptions);

  //   return this.http.get<string>(`${this.Url}user`, { headers: httpOptions });
  // }

  getUserBoard(): Observable<string> {
    return this.http.get(`${this.Url}user`, { responseType: 'text' });
  }
 
  getPMBoard(): Observable<string> {
    return this.http.get(`${this.Url}pm`, { responseType: 'text' });
  }
 
  getAdminBoard(): Observable<string> {
    return this.http.get(`${this.Url}admin`, { responseType: 'text' });
  }
}