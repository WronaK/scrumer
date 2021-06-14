import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ProjectsComponent} from "./projects/projects.component";
import {TeamsComponent} from "./teams/teams.component";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../shared/shared.module";
import { TasksComponent } from './tasks/tasks.component';

@NgModule({
  declarations: [
    DashboardComponent,
    ProjectsComponent,
    TeamsComponent,
    TasksComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    SharedModule,
  ],
  exports: [
    DashboardComponent,
  ]
})
export class DashboardModule { }
