import { Component, OnInit } from '@angular/core';
import {Task} from "../../model/task";
import {ActivatedRoute} from "@angular/router";
import {TeamsDetailsService} from "../teams-details.service";

@Component({
  selector: 'app-sprint-backlog',
  templateUrl: './sprint-backlog.component.html',
  styleUrls: ['./sprint-backlog.component.scss']
})
export class SprintBacklogComponent implements OnInit {

  tasksBacklog: Task[] = [];
  tasksPBI: Task[]  = [];
  tasksInProgress: Task[]  = [];
  tasksMergeRequest: Task[]  = [];
  tasksDone: Task[] = [];

  backlog = 'BACKLOG';
  pbi = 'PBI';
  inProgress = 'IN-PROGRESS';
  mergeRequest = 'MERGE REQUEST';
  done = 'DONE';

  constructor(
    private route: ActivatedRoute,
    private teamsDetailsService: TeamsDetailsService
  ) {}

  ngOnInit(): void {
    this.teamsDetailsService.loadsSprintBacklog();
    this.teamsDetailsService.getSprintBacklog().subscribe(
      sprintBacklog => this.tasksBacklog=sprintBacklog
    )
  }
}
