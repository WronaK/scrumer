import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";
import {User} from "../model/user";
import {PasswordValidator} from "../password.validator";
import {FormGroupErrorMatcher} from "../form.group.error.matcher";

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
  error!: string;
  errorMatcher = new FormGroupErrorMatcher('incorrectPassword');

  constructor(private router: Router,
              private authService: AuthService) {
    this.emailFC = new FormControl('', [Validators.required, Validators.email]);
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
    this.registrationForm.setValidators([PasswordValidator.isEqual(
      this.passwordFC, this.repeatPasswordFC
    )])
  }

  ngOnInit(): void {
  }

  signUp() {
    this.authService.signUp({
      name: this.nameFC.value,
      surname: this.surnameFC.value,
      email: this.emailFC.value,
      password: this.passwordFC.value,
    } as User).subscribe(
      response => {
        this.error = response;
        this.registrationForm.reset();
      },
    );
  }

  goToLogin() {
    this.router.navigate(['login']);
  }
}
