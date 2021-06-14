import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../shared/auth.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {
  loginForm: FormGroup;
  emailFC: FormControl;
  passwordFC: FormControl;

  constructor(private router: Router,
              private authService: AuthService) {
    this.emailFC = new FormControl('', [Validators.required, Validators.email]);
    this.passwordFC = new FormControl('', Validators.required);
    this.loginForm = new FormGroup({
      loginFC: this.emailFC,
      passwordFC: this.passwordFC,
    });
  }

  signIn() {
    this.authService.signIn({
      email: this.emailFC.value,
      password: this.passwordFC.value}
    )
  }

  goToRegistration() {
    this.router.navigate(['registration']);
  }
}
