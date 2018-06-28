import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/service/auth.service';
import { User } from '../../core/model/user.model';
import { RestService } from '../../core/service/rest.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private showErrorMessage = false;

  constructor(
    private authService: AuthService,
    private restService: RestService
  ) { }

  ngOnInit() {
  }

  public onSubmit(user: User): void {
    this.restService.post<User>('auth/login', user).subscribe(
      response => { this.authService.setAuthentication(response['jwtToken']); },
      error => { this.handleError(error); }
    );
  }

  private handleError(error: Error): void {
    if (error['status'] === 403) {
      this.showErrorMessage = true;
    }
  }
}
