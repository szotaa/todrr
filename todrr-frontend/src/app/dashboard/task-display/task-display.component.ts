import {Component, OnInit} from '@angular/core';
import {RestService} from "../../core/service/rest.service";
import {Task} from "../../core/model/task.model";

@Component({
  selector: 'app-task-display',
  templateUrl: './task-display.component.html',
  styleUrls: ['./task-display.component.css']
})
export class TaskDisplayComponent implements OnInit {

  private tasks : Task[] = [];

  constructor(private restService: RestService) { }

  ngOnInit() {
    this.updateTasksArray();
    console.log('size:' + this.tasks.length)
  }

  private updateTasksArray(): void{
    this.restService.getAll<Task[]>('task').subscribe(
      response => {
        this.tasks = response;
        console.log('TASKS: ' + this.tasks.length);
      }
    );
  }
}
