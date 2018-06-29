import {Component, OnInit} from '@angular/core';
import {RestService} from '../../core/service/rest.service';
import {Task} from "../../core/model/task.model";


@Component({
  selector: 'app-task-creator',
  templateUrl: './task-creator.component.html',
  styleUrls: ['./task-creator.component.css']
})
export class TaskCreatorComponent implements OnInit {

  private showErrorMessage = false;

  constructor(
    private restService: RestService,
  ) { }

  ngOnInit() {
  }


  public onSubmit(task: Task): void {
    this.restService.post<Task>('task', task).subscribe(
      response => {
        this.showErrorMessage = false;
        window.location.reload()
      },
      error => {
        this.showErrorMessage = true;
      }
    )
  }
}
