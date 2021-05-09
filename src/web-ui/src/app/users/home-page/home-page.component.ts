import { Component, OnInit } from '@angular/core';
import {PRODUCT_BACKLOG} from "../../mock/mock-product-backlog";
import {Router} from "@angular/router";
import {ProjectsService} from "../../projects/projects.service";
import {Observable} from "rxjs";
import {Project} from "../../model/project";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  tasks = PRODUCT_BACKLOG;
  projects: Project[] = [];
  teams = [{id: 1, name: "Team 1"}, {id: 2, name: "Team 2"}, {id: 3, name: "Team 3"}];

  constructor(private router: Router,
              private projectsService: ProjectsService) { }

  ngOnInit(): void {
    this.getProjects().subscribe();
  }

  goToProject(id: number): void {
    this.router.navigate(['product-backlog/' + id]);
  }

  goToTeam(id: number): void {
    this.router.navigate(['sprint-backlog/' + id]);
  }

  goJoin() {
    this.router.navigate(['projects'])
  }

  getProjects(): Observable<Project[]> {
    return this.projectsService.getProjects().pipe(
      tap(projects => {
        this.projects = projects;
      })
    )
  }
}
