import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from "../material.module";
import { DialogComponent } from './dialog/dialog.component';
import { SimpleDialogComponent } from './simple-dialog/simple-dialog.component';
import { TaskComponent } from './task/task.component';

@NgModule({
  declarations: [DialogComponent, SimpleDialogComponent, TaskComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    DialogComponent,
    SimpleDialogComponent,
    TaskComponent,
  ]
})
export class SharedModule { }
