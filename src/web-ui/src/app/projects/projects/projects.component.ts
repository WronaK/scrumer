import { Component, OnInit } from '@angular/core';
import {ProjectsService} from "../projects.service";
import {tap} from "rxjs/operators";
import {Project} from "../../model/project/project";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddProjectComponent} from "../add-project/add-project.component";
import {ProjectsSubscribeService} from "../projects-subscribe.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {

  projects: Project[] = [];
  constructor(
    private projectsService: ProjectsService,
    private dialog: MatDialog,
    private projectsSubscribeService: ProjectsSubscribeService) { }

  ngOnInit(): void {
    this.getProjects();
  }

  getProjects() {
    this.projectsSubscribeService.uploadProject();
    this.projectsSubscribeService.getProjects().pipe(tap(projects => this.projects = projects)).subscribe();
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
        tap(() => {
          this.projectsSubscribeService.uploadProject()
        })
      ).subscribe();
  }
}