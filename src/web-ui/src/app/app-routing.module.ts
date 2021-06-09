import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {AuthGuard} from "./shared/auth.guard";
import {DashboardComponent} from "./dashboard/dashboard/dashboard.component";
import {LoginPageComponent} from "./users/login-page/login-page.component";
import {RegistrationPageComponent} from "./users/registration-page/registration-page.component";
import {ProjectComponent} from "./projects/project/project.component";
import {TeamComponent} from "./teams/team/team.component";

const routes: Routes = [
  { path: '', component: MainLayoutComponent, children: [
      { path: 'project/:id', component: ProjectComponent, canActivate: [AuthGuard] },
      { path: 'team/:id', component: TeamComponent, canActivate: [AuthGuard]},
      { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]}
    ]},
  { path: 'login', component: LoginPageComponent},
  { path: 'registration', component: RegistrationPageComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
