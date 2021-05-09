import { Component, OnInit } from '@angular/core';
import {Task} from "../../mock/task";

@Component({
  selector: 'app-sprint-backlog',
  templateUrl: './sprint-backlog.component.html',
  styleUrls: ['./sprint-backlog.component.scss']
})
export class SprintBacklogComponent implements OnInit {

  tasksBacklog: Task[] = [
    {id: 9, title: "Lorem ipsum1", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20},
    {id: 10, title: "Lorem ipsum2", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20}];
  tasksPBI: Task[]  = [
    {id: 11, title: "Lorem ipsum11", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20},
    {id: 12, title: "Lorem ipsum12", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20}];
  tasksInProgress: Task[]  = [
    {id: 13, title: "Lorem ipsum13", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20},
    {id: 14, title: "Lorem ipsum14", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20}];
  tasksMergeRequest: Task[]  = [
    {id: 15, title: "Lorem ipsum15", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20},
    {id: 16, title: "Lorem ipsum16", userStory: "Utworzyć autoryzacje", priority: 1, storyPoint: 20}];
  tasksDone: Task[] = [];

  backlog = 'BACKLOG';
  pbi = 'PBI';
  inProgress = 'IN-PROGRESS';
  mergeRequest = 'MERGE REQUEST';
  done = 'DONE';

  constructor() { }

  ngOnInit(): void {
  }
}
