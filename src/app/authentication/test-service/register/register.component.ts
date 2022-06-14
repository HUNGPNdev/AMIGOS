import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../entity/auth.service';
import { SigupInfo } from '../../entity/sigup-info';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {};
  signupInfo: SigupInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
 
  constructor(private authService: AuthService) { }
 
  ngOnInit() {
  //  this.form = new SigupInfo();
   }

  onSubmit() {
    console.log(this.form);

    this.signupInfo = new SigupInfo(
      this.form.firstName,
      this.form.lastName,
      this.form.username,
      this.form.email,
      this.form.password,
      this.form.address,
      this.form.phone
    );
 
    this.authService.signUp(this.signupInfo).subscribe(
     (data:any) => {
       
          if(data.code == 400){
            alert(data.message)
          }else{
            alert("Register success")
            this.isSignedUp = true;
            this.isSignUpFailed = false;
            location.reload();
          }
       
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}