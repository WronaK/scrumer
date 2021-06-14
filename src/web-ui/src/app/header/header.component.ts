import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddProjectComponent} from "../projects/add-project/add-project.component";
import {AddTeamComponent} from "../teams/add-team/add-team.component";
import {DashboardService} from "../dashboard/dashboard.service";
import {tap} from "rxjs/operators";
import {LoginUser} from "../model/user/login.user";
import {JoinTeamComponent} from "../teams/join-team/join-team.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user!: LoginUser;
  isAdmin = false;
  constructor(private router: Router,
              private authService: AuthService,
              private dialog: MatDialog,
              private dashboardService: DashboardService) { }

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
        tap(() => this.dashboardService.uploadProject())
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
        tap(() => this.dashboardService.uploadTeams())
      ).subscribe();
  }

  joinTeam() {
    const dialogConfig= new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      request: "ADD"
    };
    this.dialog.open(JoinTeamComponent, dialogConfig)
      .afterClosed()
      .pipe(
        tap(() =>  {
          this.dashboardService.uploadTeams();
          this.dashboardService.uploadProject()
        })
      ).subscribe();
  }

  toProjects() {
    this.router.navigate(['projects']);
  }

  toTeams() {
    this.router.navigate(['teams']);
  }
}
