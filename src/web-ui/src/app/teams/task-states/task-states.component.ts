import {Component, Input } from '@angular/core';
import { Task } from '../../model/task';
import {CdkDragDrop} from "@angular/cdk/drag-drop";
import {ShareService} from "../../share.service";

@Component({
  selector: 'app-task-states',
  templateUrl: './task-states.component.html',
  styleUrls: ['./task-states.component.scss']
})
export class TaskStatesComponent {
  @Input() title!: string;
  @Input() tasks!: Task[];

  constructor(private shareService: ShareService) { }

  onDrop(event: CdkDragDrop<Task[]>) {
    this.shareService.drop(event);
  }
}
