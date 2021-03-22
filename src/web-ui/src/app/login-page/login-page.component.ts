import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loginForm: FormGroup;
  emailFC: FormControl;
  passwordFC: FormControl;

  constructor() {
    this.emailFC = new FormControl('', Validators.required);
    this.passwordFC = new FormControl('', Validators.required);
    this.loginForm = new FormGroup({
      loginFC: this.emailFC,
      passwordFC: this.passwordFC,
    });
  }

  ngOnInit(): void {
  }

}
