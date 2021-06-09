import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Team} from "../../model/team";
import {MatDialog} from "@angular/material/dialog";
import {TeamsService} from "../../teams/teams.service";
import {Router} from "@angular/router";
import {DashboardService} from "../dashboard.service";

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss']
})
export class TeamsComponent implements OnInit {

  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  teams: Team[] = [];

  constructor(
    private dialog: MatDialog,
    private teamsService: TeamsService,
    private dashboardService: DashboardService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.dashboardService.uploadTeams();
    this.dashboardService.getTeams().subscribe(teams => this.teams = teams);
  }

  goToTeam(id: number) {
    this.router.navigate(['team/' + id]);
  }

  scrollLeft(){
    this.widgetsContent.nativeElement.scrollLeft -= 250;
  }

  scrollRight(){
    this.widgetsContent.nativeElement.scrollLeft += 250;
  }
}
