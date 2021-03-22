import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { PRODUCT_BACKLOG} from '../mock/mock-product-backlog';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

class PeriodicElement {
}

@Component({
  selector: 'app-product-backlog',
  templateUrl: './product-backlog.component.html',
  styleUrls: ['./product-backlog.component.scss']
})
export class ProductBacklogComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'userStory', 'priority', 'storyPoint'];
  productBacklog = new MatTableDataSource<PeriodicElement>(PRODUCT_BACKLOG);

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.productBacklog.paginator = this.paginator;
  }
  constructor() { }

  ngOnInit(): void {
  }

}
