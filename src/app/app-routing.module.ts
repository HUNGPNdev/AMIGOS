import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './authentication/test-service/admin/admin.component';
import { CategoryComponent } from './authentication/test-service/category/category.component';
import { HomeComponent } from './authentication/test-service/home/home.component';
import { LoginComponent } from './authentication/test-service/login/login.component';
import { PmComponent } from './authentication/test-service/pm/pm.component';
import { CreateProductComponent } from './authentication/test-service/product/create-product/create-product.component';
import { ListProductComponent } from './authentication/test-service/product/list-product/list-product.component';
import { RegisterComponent } from './authentication/test-service/register/register.component';
import { UserComponent } from './authentication/test-service/user/user.component';

const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'pm',
        component: PmComponent
    },
    {
        path: 'admin',
        component: AdminComponent
    },
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
    },
    {
        path: 'category',
        component: CategoryComponent
    },
    {
        path: 'create-product',
        component: CreateProductComponent
    },
    {
        path: 'list-product',
        component: ListProductComponent
    }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
