import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductBacklogComponent} from "./product-backlog/product-backlog.component";
import {ShowTaskFromProductBacklogComponent} from "./show-task-from-product-backlog/show-task-from-product-backlog.component";
import {AddTaskToProductBacklogComponent} from "./add-task-to-product-backlog/add-task-to-product-backlog.component";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import { AddProjectComponent } from './add-project/add-project.component';
import { ProjectComponent } from './project/project.component';
import { MenuTasksComponent } from './menu-tasks/menu-tasks.component';
import { RemoveTaskComponent } from './remove-task/remove-task.component';
import { InformationProjectComponent } from './information-project/information-project.component';
import { MenuProjectComponent } from './menu-project/menu-project.component';
import { TeamsComponent } from './teams/teams.component';
import { AddTeamsComponent } from './add-teams/add-teams.component';
import { MoveTaskComponent } from './move-task/move-task.component';
import {MatRadioModule} from "@angular/material/radio";
import {SharedModule} from "../shared/shared.module";
import {PipesModule} from "../pipes/pipes.module";
import {ProjectsComponent} from "./projects/projects.component";

@NgModule({
  declarations: [
    ProductBacklogComponent,
    AddTaskToProductBacklogComponent,
    ShowTaskFromProductBacklogComponent,
    AddProjectComponent,
    ProjectComponent,
    MenuTasksComponent,
    RemoveTaskComponent,
    InformationProjectComponent,
    MenuProjectComponent,
    TeamsComponent,
    AddTeamsComponent,
    MoveTaskComponent,
    ProjectsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    MatRadioModule,
    SharedModule,
    PipesModule
  ],

  entryComponents: [
    AddTaskToProductBacklogComponent,
    AddProjectComponent,
    RemoveTaskComponent
  ],

  exports: [
    ProjectComponent,
    ProjectsComponent
  ]
})
export class ProjectsModule { }
