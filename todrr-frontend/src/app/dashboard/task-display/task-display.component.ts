import {Component, OnInit} from '@angular/core';
import {RestService} from '../../core/service/rest.service';
import {Task} from '../../core/model/task.model';

@Component({
  selector: 'app-task-display',
  templateUrl: './task-display.component.html',
  styleUrls: ['./task-display.component.css']
})
export class TaskDisplayComponent implements OnInit {

  public tasks: Task[] = [];

  constructor(private restService: RestService) { }

  ngOnInit() {
    this.updateTasksArray();
  }

  public delete(index: number): void {
    this.restService.delete('task', index).subscribe(
      response => { this.updateTasksArray(); }
    );
  }

  private updateTasksArray(): void {
    this.tasks = [];
    this.restService.getAll<Task[]>('task').subscribe(
      response => {
        this.tasks = response;
      }
    );
  }
}
