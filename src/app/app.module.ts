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
import { ProductsComponent } from './authentication/test-service/products/products.component';
import { AboutComponent } from './authentication/test-service/about/about.component';
import { ContactComponent } from './authentication/test-service/contact/contact.component';
import { BlogComponent } from './authentication/test-service/blog/blog.component';
import { AccountComponent } from './authentication/test-service/account/account.component';
import { ShoppingCartComponent } from './authentication/test-service/shopping-cart/shopping-cart.component';
import { DetailsComponent } from './authentication/test-service/details/details.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    PmComponent,
    RegisterComponent,
    UserComponent,
    ProductsComponent,
    AboutComponent,
    ContactComponent,
    BlogComponent,
    AccountComponent,
    ShoppingCartComponent,
    DetailsComponent
  ],
  imports: [
    NgxPaginationModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    Ng2SearchPipeModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
