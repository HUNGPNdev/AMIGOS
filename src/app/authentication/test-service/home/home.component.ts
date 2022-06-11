import { Component, OnInit } from '@angular/core';
import { ClientPortService } from '../../entity/client-port/client-port.service';
import { TokenStorageService } from '../../entity/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  info: any;
 
  constructor(private token: TokenStorageService,
    private clientPortService: ClientPortService) { }
 
  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
  }
 
  logout() {
    this.token.signOut();
    window.location.reload();
  }

  
}
