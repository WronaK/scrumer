import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from "../material.module";
import {SprintBacklogComponent} from "./sprint-backlog/sprint-backlog.component";
import {TaskStatesComponent} from "./task-states/task-states.component";
import {ReactiveFormsModule} from "@angular/forms";
import {DragDropModule} from "@angular/cdk/drag-drop";
import { MyTeamsComponent } from './my-teams/my-teams.component';
import { AddTeamComponent } from './add-team/add-team.component';

@NgModule({
  declarations: [
    SprintBacklogComponent,
    TaskStatesComponent,
    MyTeamsComponent,
    AddTeamComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    DragDropModule,
  ],
  exports: [
    SprintBacklogComponent,
    MyTeamsComponent
  ]
})
export class TeamsModule { }
