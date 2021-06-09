import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss']
})
export class MainLayoutComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit(): void {
  }

  goHome() {
    this.router.navigate(['home']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }
}
