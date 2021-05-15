import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ProjectsService} from "../../projects/projects.service";
import {Observable} from "rxjs";
import {Project} from "../../model/project";
import {tap} from "rxjs/operators";
import {Team} from "../../model/team";
import {TeamsService} from "../../teams/teams.service";
import {Task} from "../../model/task";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  tasks: Task[] = [];
  projects: Project[] = [];
  teams: Team[] = [];

  constructor(private router: Router,
              private projectsService: ProjectsService,
              private teamsService: TeamsService) { }

  ngOnInit(): void {
    this.getProjects().subscribe();
    this.getTeams().subscribe();
  }

  goToProject(id: number): void {
    this.router.navigate(['product-backlog/' + id]);
  }

  goToTeam(id: number): void {
    this.router.navigate(['sprint-backlog/' + id]);
  }

  goToMyProject() {
    this.router.navigate(['projects'])
  }

  goToMyTeams() {
    this.router.navigate(['teams'])
  }

  getProjects(): Observable<Project[]> {
    return this.projectsService.getProjects().pipe(
      tap(projects => {
        this.projects = projects;
      })
    )
  }

  getTeams(): Observable<Team[]> {
    return this.teamsService.getTeams().pipe(
      tap(teams => {
        this.teams = teams;
      })
    )
  }
}
