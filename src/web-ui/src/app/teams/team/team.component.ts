import { Component } from '@angular/core';
import {TeamsDetailsService} from "../teams-details.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent {

  constructor(
    private teamsDetailsService: TeamsDetailsService,
    private route: ActivatedRoute
  ) {
    route.params.subscribe(params => {
      this.teamsDetailsService.setId(parseInt(params['id']))
    })
  }
}
