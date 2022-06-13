import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BlogsService } from './authentication/entity/blog/blog.service';
import { AdminComponent } from './authentication/test-service/admin/admin.component';
import { CreateBlogComponent } from './authentication/test-service/blog/create-blog/create-blog.component';
import { ListBlogComponent } from './authentication/test-service/blog/list-blog/list-blog.component';
import { CategoryComponent } from './authentication/test-service/category/category.component';
import { HomeComponent } from './authentication/test-service/home/home.component';
import { LoginComponent } from './authentication/test-service/login/login.component';
import { PmComponent } from './authentication/test-service/pm/pm.component';
import { CreateProductSizeComponent } from './authentication/test-service/product-size/create-product-size/create-product-size.component';
import { ListProductSizeComponent } from './authentication/test-service/product-size/list-product-size/list-product-size.component';
import { CreateProductComponent } from './authentication/test-service/product/create-product/create-product.component';
import { ListProductComponent } from './authentication/test-service/product/list-product/list-product.component';
import { RegisterComponent } from './authentication/test-service/register/register.component';
import { SizeComponent } from './authentication/test-service/size/size.component';
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
    },
    {
        path: 'update-product/:id',
        component: CreateProductComponent
    },
    {
        path: 'size',
        component: SizeComponent
    },
    {
        path: 'list-product-size',
        component: ListProductSizeComponent
    },
    {
        path: 'create-product-size',
        component: CreateProductSizeComponent
    },
    {
        path: 'update-product-size/:id',
        component: CreateProductSizeComponent
    },
    /**Dunv Blog */
    {
        path: 'list-blog',
        component: ListBlogComponent
    },
    {
        path: 'create-blog',
        component: CreateBlogComponent
    },
    {
        path: 'update-blog/:id',
        component: CreateBlogComponent
    },

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
