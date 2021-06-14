import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {User} from "../../model/user/user";
import {MatPaginator} from "@angular/material/paginator";
import {TeamsDetailsService} from "../teams-details.service";

@Component({
  selector: 'app-members-team',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.scss']
})
export class MembersComponent implements OnInit, AfterViewInit {
  dataSource!: MatTableDataSource<User>;
  displayedColumns: string[] = ['name', 'surname', 'email', 'event'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  members: User[] = [];

  constructor(
    private teamsDetailsService: TeamsDetailsService
  ) {
    this.dataSource = new MatTableDataSource<User>();
    this.dataSource.data = this.members;
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.teamsDetailsService.loadsMembers();
    this.teamsDetailsService.getMembers().subscribe(
      members => {
        this.members= members;
        this.dataSource.data = members
      }
    )
  }

  remove(email: string) {
    //todo
  }
}
