import {Component, OnInit} from '@angular/core';
import {User} from '../../core/model/user.model';
import {RestService} from '../../core/service/rest.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {

  public showSuccessMessage = false;
  public showEmailTakenErrorMessage = false;
  public showErrorMessage = false;

  constructor(private restService: RestService) {

  }

  ngOnInit() {
  }

  public onSubmit(user: User): void {
    this.restService.post('user', user).subscribe(
      response => { this.handleSuccess(response); },
      error => { this.handleError(error); }
    );
  }

  private handleError(error: Error): void {
    this.clearMessages();
    if (error['status'] === 409) {
      this.showEmailTakenErrorMessage = true;
    } else {
      this.showErrorMessage = true;
    }
  }

  private handleSuccess(success: any): void {
    this.clearMessages();
    this.showSuccessMessage = true;
  }

  private clearMessages(): void {
    this.showSuccessMessage = false;
    this.showEmailTakenErrorMessage = false;
    this.showErrorMessage = false;
  }
}
