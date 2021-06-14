import {AfterViewInit, Component, ViewChild, OnInit} from '@angular/core';
import { Task } from '../../model/task/task';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {ProductBacklogService} from "../product-backlog.service";

@Component({
  selector: 'app-product-backlog',
  templateUrl: './product-backlog.component.html',
  styleUrls: ['./product-backlog.component.scss']
})
export class ProductBacklogComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'title', 'priority', 'storyPoints', 'status'];

  productBacklog: Task[] = [];

  dataSource!: MatTableDataSource<Task>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private productBacklogService: ProductBacklogService) {
    this.dataSource = new MatTableDataSource<Task>();
    this.dataSource.data = this.productBacklog;
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  onSelect(idTask: number): void {
    if(idTask != this.productBacklogService.idSelectTask) {
      this.productBacklogService.setSelectTask(idTask);
    }
  }

  ngOnInit(): void {
    this.productBacklogService.productBacklog();
    this.productBacklogService.getProductBacklog().subscribe(productBacklog =>  { this.productBacklog=productBacklog;
    this.dataSource.data = this.productBacklog});
  }

}
