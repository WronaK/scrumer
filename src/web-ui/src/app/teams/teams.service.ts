import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Team} from "../model/team";
import {HttpClient} from "@angular/common/http";
import {CreateTeam} from "../model/create.team";

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  constructor(private http: HttpClient) { }

  createTeam(team: CreateTeam) {
    return this.http.post('api/teams', team);
  }

  getTeams(): Observable<any> {
    return this.http.get<Team[]>('api/teams');
  }
}
