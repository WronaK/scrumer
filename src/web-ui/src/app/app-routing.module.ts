import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {AuthGuard} from "./shared/auth.guard";
import {MyProjectsComponent} from "./projects/my-projects/my-projects.component";
import {HomePageComponent} from "./users/home-page/home-page.component";
import {LoginPageComponent} from "./users/login-page/login-page.component";
import {RegistrationPageComponent} from "./users/registration-page/registration-page.component";
import {SprintBacklogComponent} from "./teams/sprint-backlog/sprint-backlog.component";
import {MyTeamsComponent} from "./teams/my-teams/my-teams.component";
import {ProjectComponent} from "./projects/project/project.component";

const routes: Routes = [
  { path: '', component: MainLayoutComponent, children: [
      { path: 'project/:id', component: ProjectComponent, canActivate: [AuthGuard] },
      { path: 'sprint-backlog/:id', component: SprintBacklogComponent, canActivate: [AuthGuard]},
      { path: 'home', component: HomePageComponent, canActivate: [AuthGuard]},
      { path: 'projects', component: MyProjectsComponent, canActivate: [AuthGuard]},
      { path: 'teams', component: MyTeamsComponent, canActivate: [AuthGuard]}
    ]},
  { path: 'login', component: LoginPageComponent},
  { path: 'registration', component: RegistrationPageComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
