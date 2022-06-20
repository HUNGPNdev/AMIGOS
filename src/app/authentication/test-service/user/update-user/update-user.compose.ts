import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/authentication/entity/user/User';
import { UserService } from 'src/app/authentication/entity/user/user.service';

@Component({
    selector: 'app-update-user',
    templateUrl: './update-user.component.html',
    styleUrls: ['./update-user.component.css']
  })
export class UpdateUserComponent implements OnInit {
  user: User = new User();
  users: User[];
  searchText: string;
  p:number;
  id: number = 0;
  error: '';
  isUpdateFailed = false;

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id != null) {
      this.getUserById(this.id);
    }
  }

  onSubmit() {
    if(this.id != 0) {
      this.user.id = this.id;
      this.updateById();
    }
  }

  getUserById(id: number) {
    this.userService.getUserById(id).subscribe( data => {
      this.user = data.data; 
      this.id = id;
    })
  }

  updateById() {
    this.userService.updateById(this.user).subscribe( data => {
      this.id = 0;
      alert("Update Successfully!");
      this.router.navigate(['/list-user']);
    }, error => {
      this.error = error.error.message
      this.isUpdateFailed = true
    })
  }
}
