import {Component, ElementRef, Input, OnInit, Renderer2, ViewChild} from '@angular/core';
import {Task} from "../../model/task/task";
import {Router} from "@angular/router";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {

  @Input() tasks!: Task[];

  @ViewChild('truncator') truncator!: ElementRef<HTMLElement>;
  @ViewChild('bodyText') bodyText!: ElementRef<HTMLElement>;


  constructor(private router: Router,
              private renderer: Renderer2) { }

  ngOnInit(): void {
    this.displayElement();
  }

  displayElement() {
    if (this.bodyText != null && this.truncator != null) {
      let style = window.getComputedStyle(this.bodyText.nativeElement, null);
      let viewableHeight = parseInt(style.getPropertyValue("height"), 10);

      if (this.bodyText.nativeElement.scrollHeight > viewableHeight) {
        this.renderer.setStyle(this.truncator.nativeElement, 'display', 'block');
      } else {
        this.renderer.setStyle(this.truncator.nativeElement, 'display', 'none');
      }
    }
  }
}
