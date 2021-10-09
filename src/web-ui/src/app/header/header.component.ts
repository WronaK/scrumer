import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";
import {LoginUser} from "../model/user/login.user";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user!: LoginUser;
  isAdmin = false;
  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.getUserData();
  }

  goHome() {
    this.router.navigate(['dashboard']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

  getUserData() {
    this.authService.getUserData().subscribe(user => {
      this.user = user;
      this.isAdmin = user.roles.includes("ROLE_ADMIN");
    console.log(user)});
  }

  toProjects() {
    this.router.navigate(['projects']);
  }

  toTeams() {
    this.router.navigate(['teams']);
  }

  toYourTeams() {
    this.router.navigate(['your-teams']);
  }

  toYourProjects() {
    this.router.navigate(['your-projects']);
  }

  toChat() {
    this.router.navigate(['chat']);
  }
}
