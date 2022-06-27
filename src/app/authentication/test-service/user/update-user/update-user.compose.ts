import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from 'src/app/authentication/entity/role/role';
import { RoleService } from 'src/app/authentication/entity/role/role.service';
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

  /**Combobox role */
  roles:Role[];
  rolesTemp :Role[];
  /**end */
  constructor(private userService: UserService,
    private roleService:RoleService,
    private router: Router,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id != null) {
      this.getUserById(this.id);
    }
    this.loadAllRole();
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
      let idRoles:number[] = []
      for(let d of data.data.roles){
        idRoles.push(d.id)
      }

      this.user.roleId = idRoles;
      this.id = id;
    })
  }

  updateById() {
    if(this.user.roleId){
      let roleTemp:Role[] = []
     for(let r of this.user.roleId){
      let role = this.roles.filter(c => c.id == r);
      if(role.length > 0 ){
      // Role = user[0];
        let x = role[0];
        roleTemp.push(role[0]);
      }
      this.user.roles = roleTemp; 
    }

    }
    
    this.userService.updateById(this.user).subscribe( data => {
      this.id = 0;
      alert("Update Successfully!");
      this.router.navigate(['/list-user']);
    }, error => {
      this.error = error.error.message
      this.isUpdateFailed = true
    })
  }
  loadAllRole(){
    this.roleService.listrole().subscribe(data =>{
      this.roles = data.data;
    })
  }
}
