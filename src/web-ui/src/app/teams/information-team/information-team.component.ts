import { Component, OnInit } from '@angular/core';
import {FormControl} from "@angular/forms";
import {TeamsDetailsService} from "../teams-details.service";
import {Team} from "../model/team";


@Component({
  selector: 'app-information-team',
  templateUrl: './information-team.component.html',
  styleUrls: ['./information-team.component.scss']
})
export class InformationTeamComponent implements OnInit {
  teamId!: number;
  teamNameFC: FormControl;
  passwordFC: FormControl;
  disabled = true;
  creatorFC: FormControl;

  constructor(
    private teamsDetailsService: TeamsDetailsService
  ) {
    this.teamNameFC = new FormControl({ value: '', disabled: this.disabled });
    this.passwordFC = new FormControl({ value: '', disabled: this.disabled });
    this.creatorFC = new FormControl({ value: '', disabled: this.disabled });
  }

  ngOnInit(): void {
    this.teamsDetailsService.loadsTeam();
    this.teamsDetailsService.getTeam().subscribe(
      team => {if(team != null) this.setData(team)}
    );
  }

  setData(team: Team): void {
    this.teamId = team.id;
    this.teamNameFC.setValue(team.name);
    this.passwordFC.setValue(team.accessCode);
    this.creatorFC.setValue(team.creatorName);
  }

}
