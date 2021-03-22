import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {ProductBacklogComponent} from "./product-backlog/product-backlog.component";
import {LoginPageComponent} from "./authorization/login-page/login-page.component";
import {RegistrationPageComponent} from "./authorization/registration-page/registration-page.component";
import {SprintBacklogComponent} from "./sprint-backlog/sprint-backlog.component";

const routes: Routes = [
  { path: '', component: MainLayoutComponent, children: [
      { path: 'product-backlog', component: ProductBacklogComponent },
      { path: 'sprint-backlog', component: SprintBacklogComponent }
    ]},
  { path: 'login-page', component: LoginPageComponent},
  { path: 'registration-page', component: RegistrationPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
