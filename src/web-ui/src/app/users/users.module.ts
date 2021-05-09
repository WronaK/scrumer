import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from "./login-page/login-page.component";
import { RegistrationPageComponent } from "./registration-page/registration-page.component";
import { HomePageComponent } from "./home-page/home-page.component";
import { MaterialModule } from "../material.module";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    LoginPageComponent,
    RegistrationPageComponent,
    HomePageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    LoginPageComponent,
    RegistrationPageComponent,
    HomePageComponent
  ]
})
export class UsersModule { }
