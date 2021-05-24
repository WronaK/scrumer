import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductBacklogComponent} from "./product-backlog/product-backlog.component";
import {ShowTaskFromProductBacklogComponent} from "./show-task-from-product-backlog/show-task-from-product-backlog.component";
import {MyProjectsComponent} from "./my-projects/my-projects.component";
import {AddTaskToProductBacklogComponent} from "./add-task-to-product-backlog/add-task-to-product-backlog.component";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import { AddProjectComponent } from './add-project/add-project.component';
import { ProjectComponent } from './project/project.component';
import { MenuTasksComponent } from './menu-tasks/menu-tasks.component';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { RemoveTaskComponent } from './remove-task/remove-task.component';

@NgModule({
  declarations: [
    ProductBacklogComponent,
    AddTaskToProductBacklogComponent,
    ShowTaskFromProductBacklogComponent,
    MyProjectsComponent,
    AddProjectComponent,
    ProjectComponent,
    MenuTasksComponent,
    ProjectDetailsComponent,
    RemoveTaskComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
  ],

  entryComponents: [
    AddTaskToProductBacklogComponent,
    AddProjectComponent,
    RemoveTaskComponent
  ],

  exports: [
    ProjectComponent,
    MyProjectsComponent,
  ]
})
export class ProjectsModule { }
