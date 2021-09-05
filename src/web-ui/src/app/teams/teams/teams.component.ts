import { Component, OnInit } from '@angular/core';
import {Team} from "../../model/team/team";
import {TeamsService} from "../teams.service";
import {tap} from "rxjs/operators";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddTeamComponent} from "../add-team/add-team.component";
import {JoinTeamComponent} from "../join-team/join-team.component";
import {TeamsSubscribeService} from "../teams-subscribe.service";

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss']
})
export class TeamsComponent implements OnInit {

  teams: Team[] = [];
  constructor(
    private teamsService: TeamsService,
    private teasSubscribeService: TeamsSubscribeService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams() {
    this.teasSubscribeService.uploadTeams();
    this.teasSubscribeService.getTeams().pipe(tap(teams => this.teams = teams)).subscribe();
  }

  addTeam() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      request: "ADD"
    };
    this.dialog.open(AddTeamComponent, dialogConfig)
      .afterClosed()
      .pipe(
        tap( () => {
            this.teasSubscribeService.uploadTeams()
          }
        )).subscribe();
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
        tap( () => {
          this.teasSubscribeService.uploadTeams()
        }
      )).subscribe();
  }
}
