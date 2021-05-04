import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {ProductBacklogComponent} from "./product-backlog/product-backlog.component";
import {LoginPageComponent} from "./login-page/login-page.component";
import {RegistrationPageComponent} from "./registration-page/registration-page.component";
import {SprintBacklogComponent} from "./sprint-backlog/sprint-backlog.component";
import {HomePageComponent} from "./home-page/home-page.component";
import {JoinTeamComponent} from "./join-team/join-team.component";
import {AuthGuard} from "./shared/auth.guard";

const routes: Routes = [
  { path: '', component: MainLayoutComponent, children: [
      { path: 'product-backlog/:id', component: ProductBacklogComponent, canActivate: [AuthGuard] },
      { path: 'sprint-backlog/:id', component: SprintBacklogComponent, canActivate: [AuthGuard]},
      { path: 'home', component: HomePageComponent, canActivate: [AuthGuard]}
    ]},
  { path: 'login', component: LoginPageComponent},
  { path: 'registration', component: RegistrationPageComponent},
  { path: 'join-team', component: JoinTeamComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
