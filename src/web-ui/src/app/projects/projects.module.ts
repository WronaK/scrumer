import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductBacklogComponent} from "./product-backlog/product-backlog.component";
import {ShowTaskFromProductBacklogComponent} from "./show-task-from-product-backlog/show-task-from-product-backlog.component";
import {MyProjectsComponent} from "./my-projects/my-projects.component";
import {AddTaskToProductBacklogComponent} from "./add-task-to-product-backlog/add-task-to-product-backlog.component";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import { AddProjectComponent } from './add-project/add-project.component';
import {MatCardModule} from "@angular/material/card";

@NgModule({
  declarations: [
    ProductBacklogComponent,
    AddTaskToProductBacklogComponent,
    ShowTaskFromProductBacklogComponent,
    MyProjectsComponent,
    AddProjectComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    MatCardModule,
  ],

  entryComponents: [
    AddTaskToProductBacklogComponent,
    ShowTaskFromProductBacklogComponent,
    AddProjectComponent
  ],

  exports: [
    ProductBacklogComponent,
    MyProjectsComponent,
  ]
})
export class ProjectsModule { }
