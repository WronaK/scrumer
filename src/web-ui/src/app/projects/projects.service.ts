import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Project} from "../model/project";
import {Observable} from "rxjs";
import {CreateProject} from "../model/create.project";
import {CreateTask} from "../model/create.task";
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root'
})
export class ProjectsService {

  constructor(
    private http: HttpClient
  ) { }

  createProject(project: CreateProject) {
    return this.http.post('api/projects', project);
  }

  getProjects(): Observable<any>  {
    return this.http.get<Project[]>('api/projects');
  }

  getTasksToProductBacklog(id: number) {
    return this.http.get<Task[]>('api/projects/' + id + '/product_backlog');
  }

  addTaskToProductBacklog(id: number, task: CreateTask) {
    return this.http.put<CreateTask>('api/projects/' + id + '/product_backlog', task)
  }


}
