import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Project} from "../../model/project";
import {ProjectsService} from "../projects.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddProjectComponent} from "../add-project/add-project.component";
import {switchMap, tap} from "rxjs/operators";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-projects',
  templateUrl: './my-projects.component.html',
  styleUrls: ['./my-projects.component.scss']
})
export class MyProjectsComponent implements OnInit {
  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  projects: Project[] = [];

  constructor(
    private dialog: MatDialog,
    private projectsService: ProjectsService,
    private router: Router) { }

  ngOnInit(): void {
    this.getProjects().subscribe();
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
        switchMap(() => this.getProjects())
      ).subscribe();
  }

  getProjects(): Observable<Project[]> {
    return this.projectsService.getProjects().pipe(
      tap(projects => {
        this.projects = projects;
      })
    )
  }

  goToProject(id: number): void {
    this.router.navigate(['project/' + id]);
  }

  scrollLeft(){
    this.widgetsContent.nativeElement.scrollLeft -= 250;
  }

  scrollRight(){
    this.widgetsContent.nativeElement.scrollLeft += 250;
  }
}
