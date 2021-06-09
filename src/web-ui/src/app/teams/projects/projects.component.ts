import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Project} from "../model/project";
import {MatPaginator} from "@angular/material/paginator";
import {TeamsDetailsService} from "../teams-details.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {
  dataSource!: MatTableDataSource<Project>;
  displayedColumns: string[] = ['id', 'name', 'event'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  projects: Project[] = [];

  constructor(
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
    //todo
  }
}
