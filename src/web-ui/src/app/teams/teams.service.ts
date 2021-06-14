import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Team} from "../model/team/team";
import {TeamDetails as TTeam} from "../model/team/team.details";
import {HttpClient} from "@angular/common/http";
import {CreateTeam} from "../model/team/create.team";
import {User} from "../model/user/user";
import {Project} from "../model/project/project";
import {UpdateTeam} from "../model/team/update.team";
import {Members} from "../model/user/member";
import {JoinProject} from "../model/project/join.project";
import {SprintBacklog} from "../model/project/sprint.backlog";
import {JoinTeam} from "../model/team/join.team";

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

  getAllTeams(): Observable<any> {
    return this.http.get<Team[]>(this.url);
  }

  getTeams(): Observable<any> {
    return this.http.get<Team[]>(this.url + 'my-teams');
  }

  getTasksSprintBacklog(id: number): Observable<SprintBacklog> {
    return this.http.get<SprintBacklog>(this.url + id + '/sprint_backlog');
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

  addMembers(id: number, members: Members) {
    return this.http.put<Members>(this.url + id + '/members', members);
  }

  joinProject(id: number, projects: JoinProject) {
    return this.http.put<JoinProject>(this.url + id + '/projects', projects);
  }

  joinTeam(team: JoinTeam) {
    return this.http.put<JoinTeam>('api/users/join', team);
  }

  removeProjectWithTeam(id: number, idProject: number) {
    return this.http.patch(this.url + id + "/projects/" + idProject + "/remove", null);
  }
}
