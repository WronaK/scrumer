import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Team} from "../../model/team/team";
import {MatDialog} from "@angular/material/dialog";
import {TeamsService} from "../../teams/teams.service";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";

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
    private router: Router
  ) { }

  ngOnInit(): void {
    this.isDashboard = this.router.isActive('dashboard', true);
    if(this.isDashboard) {
      this.teamsService.getTeams().pipe(tap(teams => this.teams = teams)).subscribe();
    } else {
      this.teamsService.getAllTeams().pipe(tap(teams => this.teams = teams)).subscribe();
    }
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
