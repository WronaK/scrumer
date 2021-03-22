import { Component, OnInit } from '@angular/core';
import {PRODUCT_BACKLOG} from "../mock/mock-product-backlog";

@Component({
  selector: 'app-sprint-backlog',
  templateUrl: './sprint-backlog.component.html',
  styleUrls: ['./sprint-backlog.component.scss']
})
export class SprintBacklogComponent implements OnInit {

  tasksBacklog = PRODUCT_BACKLOG;
  tasksPBI = PRODUCT_BACKLOG;
  tasksInProgress = PRODUCT_BACKLOG;
  tasksMergeRequest = PRODUCT_BACKLOG;
  tasksDone = [];

  backlog = 'BACKLOG';
  pbi = 'PBI';
  inProgress = 'IN-PROGRESS';
  mergeRequest = 'MERGE REQUEST';
  done = 'DONE';

  constructor() { }

  ngOnInit(): void {
  }
}
