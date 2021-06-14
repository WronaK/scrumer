import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Task} from "../../model/task/task";
import {Router} from "@angular/router";
import {TaskService} from "../../task.service";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {
  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
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

  scrollTop(){
    this.widgetsContent.nativeElement.scrollTop -= 250;
  }

  scrollDown(){
    this.widgetsContent.nativeElement.scrollTop += 250;
  }
}
