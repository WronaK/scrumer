import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from "../material.module";
import { DialogComponent } from './dialog/dialog.component';
import { SimpleDialogComponent } from './simple-dialog/simple-dialog.component';

@NgModule({
  declarations: [DialogComponent, SimpleDialogComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    DialogComponent,
    SimpleDialogComponent
  ]
})
export class SharedModule { }
