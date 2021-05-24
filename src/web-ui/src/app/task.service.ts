import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import {HttpClient} from "@angular/common/http";
import { Task } from './model/task';

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

  updateTask(task: Task): Observable<any> {
    return this.http.put<Task>(this.url, task);
  }

  removeTask(id: number) {
    return this.http.delete(this.url + id);
  }
}
