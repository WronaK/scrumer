import {Component, Input, OnInit} from '@angular/core';
import { Task } from '../mock/task';

@Component({
  selector: 'app-task-states',
  templateUrl: './task-states.component.html',
  styleUrls: ['./task-states.component.scss']
})
export class TaskStatesComponent implements OnInit {
  @Input() title!: string;
  @Input() tasks!: Task[];

  constructor() { }

  ngOnInit(): void {
  }

}
