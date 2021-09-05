import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {ProjectDetails} from "../model/project/project.details";
import {ProjectsService} from "./projects.service";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ProjectsSubscribeService {

  private project$: BehaviorSubject<ProjectDetails[]> = new BehaviorSubject<ProjectDetails[]>([]);

  constructor(private projectService: ProjectsService) { };

  getProjects(): Observable<ProjectDetails[]> {
    return this.project$.asObservable();
  }

  setProject(project: ProjectDetails[]) {
    this.project$.next(project);
  }

  uploadProject() {
    this.projectService.getProjects()
      .pipe(tap(project => this.setProject(project))).subscribe();
  }
}
