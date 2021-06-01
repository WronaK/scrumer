import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import {HttpClient} from "@angular/common/http";
import { Task } from './model/task';
import {Subtasks} from "./model/create.task";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private url = 'api/tasks/';
  constructor(
    private http: HttpClient
  ) { }

  getTask(id: number): Observable<Task> {
    return this.http.get<Task>(this.url + id);
  }

  getSubtask(id: number): Observable<Task> {
    return this.http.get<Task>('api/subtasks/' + id);
  }

  updateTask(task: Task): Observable<any> {
    return this.http.put<Task>(this.url, task);
  }

  removeTask(id: number) {
    return this.http.delete(this.url + id);
  }

  addTaskToTeam(id: number, idTask: number) {
    return this.http.patch("api/teams/" + id + "/task/" + idTask, null);
  }

  addSubtasks(id: number, subtasks: Subtasks) {
    return this.http.put<Subtasks>(this.url + id + "/subtasks", subtasks);
  }

  changeStatusTask(id: number) {
    return this.http.patch("api/subtasks/" + id, null);
  }
}
