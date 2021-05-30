import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Team} from "../model/team";
import {Team as TTeam} from "./model/team";
import {HttpClient} from "@angular/common/http";
import {CreateTeam} from "../model/create.team";
import {Task} from "../model/task";
import {User} from "../model/user";
import {Project} from "./model/project";
import {UpdateTeam} from "./model/update-team";

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  private url = 'api/teams/';
  constructor(private http: HttpClient) { }

  createTeam(team: CreateTeam) {
    return this.http.post(this.url, team);
  }

  getTeamById(id: number): Observable<any> {
    return this.http.get<TTeam>(this.url + id);
  }

  getTeams(): Observable<any> {
    return this.http.get<Team[]>(this.url);
  }

  getTasksSprintBacklog(id: number): Observable<Task[]> {
    return this.http.get<Task[]>(this.url + id + '/sprint_backlog');
  }

  getProjectsById(id: number): Observable<any> {
    return this.http.get<Project[]>(this.url + id + '/projects');
  }

  getMembers(id: number) {
    return this.http.get<User[]>(this.url + id + '/members');
  }

  updateTeam(team: UpdateTeam) {
    return this.http.put<UpdateTeam>(this.url, team);
  }
}
