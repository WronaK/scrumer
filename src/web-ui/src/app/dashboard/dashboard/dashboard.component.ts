import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {Task} from "../../model/task";
import {TaskService} from "../../task.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private router: Router,
              private tasksService: TaskService) { }

  ngOnInit(): void {
    this.getSubtasks().subscribe();
  }

  getSubtasks(): Observable<Task[]> {
    return this.tasksService.getSubtasks().pipe(
      tap(subtasks => {
        this.tasks = subtasks;
      })
    )
  }
}
