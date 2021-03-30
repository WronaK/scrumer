import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.scss']
})
export class RegistrationPageComponent implements OnInit {
  emailFC: FormControl;
  nameFC: FormControl;
  surnameFC: FormControl;
  passwordFC: FormControl;
  repeatPasswordFC: FormControl;
  registrationForm: FormGroup;

  constructor(private router: Router) {
    this.emailFC = new FormControl('', Validators.required);
    this.nameFC = new FormControl('', Validators.required);
    this.surnameFC = new FormControl('', Validators.required);
    this.passwordFC = new FormControl('', Validators.required);
    this.repeatPasswordFC = new FormControl('', Validators.required);
    this.registrationForm = new FormGroup({
      loginFC: this.emailFC,
      nameFC: this.nameFC,
      surnameFC: this.surnameFC,
      passwordFC: this.passwordFC,
      repeatPasswordFC: this.repeatPasswordFC
    });
  }

  ngOnInit(): void {
  }

  goToLogin() {
    this.router.navigate(['login']);
  }
}
