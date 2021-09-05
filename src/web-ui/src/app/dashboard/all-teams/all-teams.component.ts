import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Team} from "../../model/team/team";
import {MatDialog} from "@angular/material/dialog";
import {TeamsService} from "../../teams/teams.service";
import {Router} from "@angular/router";
import {DashboardService} from "../dashboard.service";

@Component({
  selector: 'app-teams',
  templateUrl: './all-teams.component.html',
  styleUrls: ['./all-teams.component.scss']
})
export class AllTeamsComponent implements OnInit {

  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  teams: Team[] = [];
  isDashboard!: boolean;

  constructor(
    private dialog: MatDialog,
    private teamsService: TeamsService,
    private dashboardService: DashboardService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.isDashboard = this.router.isActive('dashboard', true);
    if(this.isDashboard) {
      this.dashboardService.uploadTeams();
    } else {
      this.dashboardService.uploadAllTeams();
    }
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
