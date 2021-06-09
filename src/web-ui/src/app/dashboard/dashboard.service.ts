import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Project} from "../model/project";
import {Team} from "../model/team";
import {ProjectsService} from "../projects/projects.service";
import {tap} from "rxjs/operators";
import {TeamsService} from "../teams/teams.service";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private project$: BehaviorSubject<Project[]> = new BehaviorSubject<Project[]>([]);
  private teams$: BehaviorSubject<Team[]> = new BehaviorSubject<Team[]>([]);

  constructor(private projectService: ProjectsService,
              private teamsService: TeamsService) { };

  getProjects(): Observable<Project[]> {
    return this.project$.asObservable();
  }

  setProject(project: Project[]) {
    this.project$.next(project);
  }

  getTeams(): Observable<Team[]> {
    return this.teams$.asObservable();
  }

  setTeam(teams: Team[]) {
    this.teams$.next(teams);
  }

  uploadProject() {
    this.projectService.getProjects()
      .pipe(tap(project => this.setProject(project))).subscribe();
  }

  uploadTeams() {
    this.teamsService.getTeams()
      .pipe(tap(teams => this.setTeam(teams))).subscribe();
  }
}
