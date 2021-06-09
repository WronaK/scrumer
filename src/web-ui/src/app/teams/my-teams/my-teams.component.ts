import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Team} from "../../model/team";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddTeamComponent} from "../add-team/add-team.component";
import {switchMap, tap} from "rxjs/operators";
import {Observable} from "rxjs";
import {TeamsService} from "../teams.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-teams',
  templateUrl: './my-teams.component.html',
  styleUrls: ['./my-teams.component.scss']
})
export class MyTeamsComponent implements OnInit {
  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  teams: Team[] = [];

  constructor(
    private dialog: MatDialog,
    private teamsService: TeamsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getTeams().subscribe();
  }

  addTeam() {
    const dialogConfig= new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      request: "ADD"
    };
    this.dialog.open(AddTeamComponent, dialogConfig)
      .afterClosed()
      .pipe(
        switchMap(() => this.getTeams())
      ).subscribe();
  }

  getTeams(): Observable<Team[]> {
    return this.teamsService.getTeams().pipe(
      tap( teams => {
        this.teams = teams;
      })
    )
  }

  goToTeam(id: number) {
    this.router.navigate(['team/' + id]);
  }

  scrollLeft(){
    this.widgetsContent.nativeElement.scrollLeft -= 150;
  }

  scrollRight(){
    this.widgetsContent.nativeElement.scrollLeft += 150;
  }
}
