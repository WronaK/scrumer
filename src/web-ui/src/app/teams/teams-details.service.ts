import { Injectable } from '@angular/core';
import {TeamsService} from "./teams.service";
import {BehaviorSubject, Observable} from "rxjs";
import {Project} from "./model/project";
import {User} from "../model/user";
import {tap} from "rxjs/operators";
import {Task} from "../model/task";
import {Team} from "./model/team";

@Injectable({
  providedIn: 'root'
})
export class TeamsDetailsService {

  idTeam!: number;
  private sprintBacklog$: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>([]);

  private team$: BehaviorSubject<Team | null> = new BehaviorSubject<Team | null>(null);
  private projects$: BehaviorSubject<Project[]> = new BehaviorSubject<Project[]>([]);
  private members$: BehaviorSubject<User[]> = new BehaviorSubject<User[]>([]);

  constructor(private teamService: TeamsService) { }

  setId(id: number) {
    this.idTeam = id;
  }

  getSprintBacklog(): Observable<Task[]> {
    return this.sprintBacklog$.asObservable();
  }

  setSprintBacklog(sprintBacklog: Task[]) {
    this.sprintBacklog$.next(sprintBacklog);
  }

  getTeam(): Observable<Team | null> {
    return this.team$.asObservable();
  }

  setTeam(team: Team) {
    this.team$.next(team);
  }

  getProjects(): Observable<Project[]> {
    return this.projects$.asObservable();
  }

  setProjects(projects: Project[]) {
    this.projects$.next(projects);
  }

  getMembers(): Observable<User[]> {
    return this.members$.asObservable();
  }

  setMembers(members: User[]) {
    this.members$.next(members);
  }

  loadsSprintBacklog() {
    this.teamService.getTasksSprintBacklog(this.idTeam).pipe(
      tap(sprintBacklog => this.setSprintBacklog(sprintBacklog))
    ).subscribe();
  }

  loadsTeam() {
    this.teamService.getTeamById(this.idTeam).pipe(
      tap(team => this.setTeam(team))
    ).subscribe();
  }

  loadsProjects() {
    this.teamService.getProjectsById(this.idTeam).pipe(
      tap(projects => this.setProjects(projects))
    ).subscribe();
  }

  loadsMembers() {
    this.teamService.getMembers(this.idTeam).pipe(
      tap(members => this.setMembers(members))
    ).subscribe();
  }
}
