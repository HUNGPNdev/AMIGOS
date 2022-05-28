import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { httpInterceptorProviders } from './authentication/entity/auth-interceptor';
import { AdminComponent } from './authentication/test-service/admin/admin.component';
import { HomeComponent } from './authentication/test-service/home/home.component';
import { LoginComponent } from './authentication/test-service/login/login.component';
import { PmComponent } from './authentication/test-service/pm/pm.component';
import { RegisterComponent } from './authentication/test-service/register/register.component';
import { UserComponent } from './authentication/test-service/user/user.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    PmComponent,
    RegisterComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
