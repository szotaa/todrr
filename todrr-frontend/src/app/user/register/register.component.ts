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
  public showPasswordsDoNotMatch = false;

  constructor(private restService: RestService) {}

  ngOnInit() {}

  public onSubmit(user: User): void {
    if (this.arePasswordsMatching(user.password, user.passwordRepeat)) {
      this.restService.post('user', user).subscribe(
        response => { this.handleSuccess(response); },
        error => { this.handleError(error); }
      );
    } else {
      this.showPasswordsDoNotMatch = true;
    }
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
    this.showPasswordsDoNotMatch = false;
  }

  private arePasswordsMatching(password: string, repeatedPassword: string): boolean {
    if(password === repeatedPassword){
      return true;
    }
    return false;
  }
}
