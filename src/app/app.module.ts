import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
 import { NgxPaginationModule } from 'ngx-pagination';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { httpInterceptorProviders } from './authentication/entity/auth-interceptor';
import { AdminComponent } from './authentication/test-service/admin/admin.component';
import { CategoryComponent } from './authentication/test-service/category/category.component';
import { HomeComponent } from './authentication/test-service/home/home.component';
import { LoginComponent } from './authentication/test-service/login/login.component';
import { PmComponent } from './authentication/test-service/pm/pm.component';
import { RegisterComponent } from './authentication/test-service/register/register.component';
import { UserComponent } from './authentication/test-service/user/user.component';
import { CreateProductComponent } from './authentication/test-service/product/create-product/create-product.component';
import { ListProductComponent } from './authentication/test-service/product/list-product/list-product.component';
import { ContactComponent } from './authentication/test-service/contact/contact.component';
import { SizeComponent } from './authentication/test-service/size/size.component';
import { CreateProductSizeComponent } from './authentication/test-service/product-size/create-product-size/create-product-size.component';
import { ListProductSizeComponent } from './authentication/test-service/product-size/list-product-size/list-product-size.component';
import { ListUserComponent } from './authentication/test-service/user/list-user/list-user.component';
import { UpdateUserComponent } from './authentication/test-service/user/update-user/update-user.compose';
import { OrderComponent } from './authentication/test-service/order/order.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    PmComponent,
    RegisterComponent,
    UserComponent,
    CategoryComponent,
    CreateProductComponent,
    ListProductComponent,
    ContactComponent,
    SizeComponent,
    CreateProductSizeComponent,
    ListProductSizeComponent,
    ListUserComponent,
    UpdateUserComponent,
    OrderComponent
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
