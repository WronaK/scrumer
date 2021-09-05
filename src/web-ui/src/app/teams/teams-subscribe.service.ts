import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Team} from "../model/team/team";
import {TeamsService} from "./teams.service";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class TeamsSubscribeService {

  private teams$: BehaviorSubject<Team[]> = new BehaviorSubject<Team[]>([]);

  constructor(private teamsService: TeamsService) { };

  getTeams(): Observable<Team[]> {
    return this.teams$.asObservable();
  }

  setTeam(teams: Team[]) {
    this.teams$.next(teams);
  }

  uploadTeams() {
    this.teamsService.getTeams()
      .pipe(tap(teams => this.setTeam(teams))).subscribe();
  }
}
