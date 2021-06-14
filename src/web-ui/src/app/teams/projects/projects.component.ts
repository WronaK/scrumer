import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Project} from "../../model/project/project";
import {MatPaginator} from "@angular/material/paginator";
import {TeamsDetailsService} from "../teams-details.service";
import {TeamsService} from "../teams.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit, AfterViewInit {
  dataSource!: MatTableDataSource<Project>;
  displayedColumns: string[] = ['id', 'name', 'event'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  projects: Project[] = [];

  constructor(
    private teamsService: TeamsService,
    private teamsDetailsService: TeamsDetailsService
  ) {
    this.dataSource = new MatTableDataSource<Project>();
    this.dataSource.data = this.projects;
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.teamsDetailsService.loadsProjects();
    this.teamsDetailsService.getProjects().subscribe(
      projects => {
        this.projects = projects;
        this.dataSource.data = projects
      }
    )
  }

  remove(id: number) {
    this.teamsService.removeProjectWithTeam(this.teamsDetailsService.idTeam, id).subscribe(
      () => this.teamsDetailsService.loadsProjects()
    );
  }
}
