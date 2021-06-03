import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from "./login-page/login-page.component";
import { RegistrationPageComponent } from "./registration-page/registration-page.component";
import { MaterialModule } from "../material.module";
import { ReactiveFormsModule } from "@angular/forms";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  declarations: [
    LoginPageComponent,
    RegistrationPageComponent,

  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    SharedModule,
  ],
  exports: [
    LoginPageComponent,
    RegistrationPageComponent,

  ]
})
export class UsersModule { }
