import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProjectsService} from "../projects.service";
import {ProductBacklogService} from "../product-backlog.service";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectsService,
    private productBacklogService: ProductBacklogService
  ) {
    route.params.subscribe(params => this.productBacklogService.setId(parseInt(params['id'])));
  }

  onSelect() {
    return this.productBacklogService.idSelectTask;
  }
}
