import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './authentication/test-service/about/about.component';
import { AccountComponent } from './authentication/test-service/account/account.component';
import { AdminComponent } from './authentication/test-service/admin/admin.component';
import { BlogComponent } from './authentication/test-service/blog/blog.component';
import { CartOrderComponent } from './authentication/test-service/cart-order/cart-order.component';
import { ContactComponent } from './authentication/test-service/contact/contact.component';
import { DetailsComponent } from './authentication/test-service/details/details.component';
import { HomeComponent } from './authentication/test-service/home/home.component';
import { LoginComponent } from './authentication/test-service/login/login.component';
import { PmComponent } from './authentication/test-service/pm/pm.component';
import { ProductsComponent } from './authentication/test-service/products/products.component';
import { RegisterComponent } from './authentication/test-service/register/register.component';
import { ShoppingCartComponent } from './authentication/test-service/shopping-cart/shopping-cart.component';
import { UserComponent } from './authentication/test-service/user/user.component';

const routes: Routes = [
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
    path: 'products/:cateId',
    component: ProductsComponent
},
{
    path: 'about',
    component: AboutComponent
},
{
    path: 'contact',
    component: ContactComponent
},
{
    path: 'blog/:blogId',
    component: BlogComponent
},
{
    path: 'blog',
    component: BlogComponent
},
{
    path: 'account',
    component: AccountComponent
},
{
    path: 'shopping-cart',
    component: ShoppingCartComponent
},
{
    path: 'details/:productId',
    component: DetailsComponent
},
{
    path: 'cart-order',
    component: CartOrderComponent
},
{
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
