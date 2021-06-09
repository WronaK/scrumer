import { Injectable } from '@angular/core';
import {ProjectsService} from "./projects.service";
import {BehaviorSubject, Observable} from "rxjs";
import { Task } from '../model/task';
import {tap} from "rxjs/operators";
import {TaskService} from "../task.service";

@Injectable({
  providedIn: 'root'
})
export class ProductBacklogService {
  private productBacklog$: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>([]);
  private selectedTask$: BehaviorSubject<Task | null> = new BehaviorSubject<Task | null>(null);
  idProject!: number;
  idSelectTask!: number | null;

  constructor(
    private projectsService: ProjectsService,
    private tasksService: TaskService) {
  }

  setId(id: number) {
    this.idProject = id;
  }

  getProductBacklog(): Observable<Task[]> {
    return this.productBacklog$.asObservable();
  }

  setProductBacklog(productBacklog: Task[]) {
    this.productBacklog$.next(productBacklog);
  }

  getSelectedTask(): Observable<Task | null> {
    return this.selectedTask$.asObservable();
  }

  setSelectedTask(task: Task) {
      this.selectedTask$.next(task);
  }


  setSelectTask(id: number | null) {
    this.idSelectTask = id;
    this.selectedTask();
  }

  productBacklog() {
    this.projectsService.getTasksToProductBacklog(this.idProject)
      .pipe(tap(productBacklog => this.setProductBacklog(productBacklog))).subscribe();
  }

  selectedTask() {
    if(this.idSelectTask != null) {
      this.tasksService.getTask(this.idSelectTask)
        .pipe(tap(task => this.setSelectedTask(task))).subscribe();
    }
  }


}
