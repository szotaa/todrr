import { Component, OnInit } from '@angular/core';
import {Task} from '../core/model/task.model';
import {RestService} from '../core/service/rest.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  private pathId;
  public task: Task;

  constructor(
    private restService: RestService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe( x => this.pathId = x.id);
    this.fetchTask();
  }

  public fetchTask(): void {
    this.restService.getOne<Task>('task', this.pathId).subscribe(
      x => this.task = x
    );
  }

  public update(task: Task): void {
    this.restService.put<Task>('task', this.pathId, task).subscribe(
      x => this.fetchTask()
    );
  }

  public delete(index: number): void {
    this.restService.delete<Task>('task', index).subscribe(
      x => { this.router.navigate(['/tasks']); }
    );
  }
}
