import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Project} from "../../model/project";
import {ProjectsService} from "../../projects/projects.service";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {

  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  projects: Project[] = [];

  constructor(
    private projectsService: ProjectsService,
    private router: Router) { }

  ngOnInit(): void {
    this.getProjects().subscribe();
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
