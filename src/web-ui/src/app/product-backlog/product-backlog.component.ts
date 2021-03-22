import {AfterViewInit, Component, OnInit} from '@angular/core';
import { PRODUCT_BACKLOG} from '../mock/mock-product-backlog';

@Component({
  selector: 'app-product-backlog',
  templateUrl: './product-backlog.component.html',
  styleUrls: ['./product-backlog.component.scss']
})
export class ProductBacklogComponent implements OnInit {
  displayedColumns: string[] = ['ID', 'TITLE TASK', 'PRIRIORITY', 'STORY POINT(S)'];
  productBacklog = PRODUCT_BACKLOG;

  constructor() { }

  ngOnInit(): void {
  }

}
