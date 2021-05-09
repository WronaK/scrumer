import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from "../material.module";
import {SprintBacklogComponent} from "./sprint-backlog/sprint-backlog.component";
import {TaskStatesComponent} from "./task-states/task-states.component";
import {ReactiveFormsModule} from "@angular/forms";
import {DragDropModule} from "@angular/cdk/drag-drop";

@NgModule({
  declarations: [
    SprintBacklogComponent,
    TaskStatesComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    DragDropModule,
  ],
  exports: [
    SprintBacklogComponent
  ]
})
export class TeamsModule { }
