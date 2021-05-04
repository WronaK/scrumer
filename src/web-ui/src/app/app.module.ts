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
import { LoginPageComponent } from './login-page/login-page.component';
import {ReactiveFormsModule} from "@angular/forms";
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import {MatMenuModule} from "@angular/material/menu";
import { HomePageComponent } from './home-page/home-page.component';
import { JoinTeamComponent } from './join-team/join-team.component';
import {MatExpansionModule} from "@angular/material/expansion";
import {AddTaskToProductBacklogComponent} from "./add-task-to-product-backlog/add-task-to-product-backlog.component";
import {ShowTaskFromProductBacklogComponent} from "./show-task-from-product-backlog/show-task-from-product-backlog.component";
import { SprintBacklogComponent } from './sprint-backlog/sprint-backlog.component';
import { TaskStatesComponent } from './task-states/task-states.component';
import {DragDropModule} from "@angular/cdk/drag-drop";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "./shared/auth.interceptor";


@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    ProductBacklogComponent,
    LoginPageComponent,
    RegistrationPageComponent,
    HomePageComponent,
    JoinTeamComponent,
    AddTaskToProductBacklogComponent,
    ShowTaskFromProductBacklogComponent,
    SprintBacklogComponent,
    TaskStatesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatExpansionModule,
    MatMenuModule,
    DragDropModule,
    HttpClientModule
  ],

  entryComponents: [
    AddTaskToProductBacklogComponent,
    ShowTaskFromProductBacklogComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
