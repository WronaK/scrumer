import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {ProjectDetails} from "../model/project/project.details";
import {Team} from "../model/team/team";
import {ProjectsService} from "../projects/projects.service";
import {tap} from "rxjs/operators";
import {TeamsService} from "../teams/teams.service";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private project$: BehaviorSubject<ProjectDetails[]> = new BehaviorSubject<ProjectDetails[]>([]);
  private teams$: BehaviorSubject<Team[]> = new BehaviorSubject<Team[]>([]);

  constructor(private projectService: ProjectsService,
              private teamsService: TeamsService) { };

  getProjects(): Observable<ProjectDetails[]> {
    return this.project$.asObservable();
  }

  setProject(project: ProjectDetails[]) {
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

  uploadAllProject() {
    this.projectService.getAllProjects()
      .pipe(tap(project => this.setProject(project))).subscribe();
  }

  uploadTeams() {
    this.teamsService.getTeams()
      .pipe(tap(teams => this.setTeam(teams))).subscribe();
  }

  uploadAllTeams() {
    this.teamsService.getAllTeams()
      .pipe(tap(teams => this.setTeam(teams))).subscribe();
  }
}
