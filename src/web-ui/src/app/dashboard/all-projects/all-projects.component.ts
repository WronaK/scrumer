import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ProjectDetails} from "../../model/project/project.details";
import {ProjectsService} from "../../projects/projects.service";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.scss']
})
export class AllProjectsComponent implements OnInit {

  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  projects: ProjectDetails[] = [];
  isDashboard!: boolean;

  constructor(
    private projectsService: ProjectsService,
    private router: Router) { }

  ngOnInit(): void {
    this.isDashboard = this.router.isActive('dashboard', true);
    if(this.isDashboard) {
      this.projectsService.getProjects().pipe(tap(projects => this.projects = projects)).subscribe();
    } else {
      this.projectsService.getAllProjects().pipe(tap(projects => this.projects = projects)).subscribe();
    }
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
