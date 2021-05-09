import {Component, Input, OnInit} from '@angular/core';
import { Task } from '../../mock/task';
import {CdkDragDrop} from "@angular/cdk/drag-drop";
import {ShareService} from "../../share.service";

@Component({
  selector: 'app-task-states',
  templateUrl: './task-states.component.html',
  styleUrls: ['./task-states.component.scss']
})
export class TaskStatesComponent implements OnInit {
  @Input() title!: string;
  @Input() tasks!: Task[];

  constructor(private shareService: ShareService) { }

  ngOnInit(): void {
  }

  onDrop(event: CdkDragDrop<Task[]>) {
    this.shareService.drop(event);
  }
}
