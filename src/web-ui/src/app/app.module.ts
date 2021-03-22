import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainLayoutComponent } from './main-layout/main-layout.component';
import { ProductBacklogComponent } from './product-backlog/product-backlog.component';
import { MaterialModule } from './material.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSelectModule} from "@angular/material/select";
import { LoginPageComponent } from './authorization/login-page/login-page.component';
import {ReactiveFormsModule} from "@angular/forms";
import { RegistrationPageComponent } from './authorization/registration-page/registration-page.component';
import {MatMenuModule} from "@angular/material/menu";
import { SprintBacklogComponent } from './sprint-backlog/sprint-backlog.component';
import {MatExpansionModule} from "@angular/material/expansion";
import { TaskStatesComponent } from './task-states/task-states.component';


@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    ProductBacklogComponent,
    LoginPageComponent,
    RegistrationPageComponent,
    SprintBacklogComponent,
    TaskStatesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatExpansionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
