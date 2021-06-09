import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {tap} from "rxjs/operators";
import {ProjectDetailsService} from "../project-details.service";
import {AddProjectComponent} from "../add-project/add-project.component";
import {AddTeamsComponent} from "../add-teams/add-teams.component";

@Component({
  selector: 'app-menu-project',
  templateUrl: './menu-project.component.html',
  styleUrls: ['./menu-project.component.scss']
})
export class MenuProjectComponent implements OnInit {

  constructor(
    private dialog: MatDialog,
    private projectDetailsService: ProjectDetailsService
  ) { }

  ngOnInit(): void {
  }

  updateProject() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: this.projectDetailsService.idProject,
      request: "UPDATE"
    };
    this.dialog.open(AddProjectComponent, dialogConfig)
      .afterClosed().pipe(
      tap(() => {
        this.projectDetailsService.uploadProject()
      })
    ).subscribe();
  }

  addTeams() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: this.projectDetailsService.idProject,
    };
    this.dialog.open(AddTeamsComponent, dialogConfig)
      .afterClosed().pipe(
      tap(() => {
        this.projectDetailsService.uploadTeams()
      })
    ).subscribe();
  }
}
