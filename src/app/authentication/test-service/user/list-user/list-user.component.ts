import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/authentication/entity/user/User';
import { UserService } from 'src/app/authentication/entity/user/user.service';

@Component({
    selector: 'app-list-user',
    templateUrl: './list-user.component.html',
    styleUrls: ['./list-user.component.css']
  })
export class ListUserComponent implements OnInit {
  user: User = new User();
  users: User[];
  searchText: string;
  p:number;
  id: number = 0;

  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllUser();
  }

  getAllUser() {
    this.userService.listUser().subscribe( data => {
      this.users = data.data;
    }, error => console.log(error))
  }

  deleteById(id: number) {
    this.userService.deleteById(id).subscribe( data => {
      this.getAllUser();
    }, error => console.log(error))
  }

  goToUpdate(id: number) {
    this.router.navigate(['/update-user', id]);
  }
}
