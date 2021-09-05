import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AllProjectsComponent} from "./all-projects/all-projects.component";
import {AllTeamsComponent} from "./all-teams/all-teams.component";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../shared/shared.module";
import { TasksComponent } from './tasks/tasks.component';

@NgModule({
  declarations: [
    DashboardComponent,
    AllProjectsComponent,
    AllTeamsComponent,
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
