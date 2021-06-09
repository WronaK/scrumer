import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddProjectComponent} from "../projects/add-project/add-project.component";
import {AddTeamComponent} from "../teams/add-team/add-team.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user!: User;
  constructor(private router: Router,
              private authService: AuthService,
              private dialog: MatDialog) { }

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
    this.authService.getUserData().subscribe(user => this.user = user);
  }

  addProject() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      request: "ADD"
    };
    this.dialog.open(AddProjectComponent, dialogConfig)
      .afterClosed()
      .pipe(
        // switchMap(() => this.getProjects())
      ).subscribe();
  }

  addTeam() {
    const dialogConfig= new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      request: "ADD"
    };
    this.dialog.open(AddTeamComponent, dialogConfig)
      .afterClosed()
      .pipe(
        // switchMap(() => this.getTeams())
      ).subscribe();
  }

}
