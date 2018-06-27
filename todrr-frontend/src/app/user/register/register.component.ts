import {Component, OnInit} from '@angular/core';
import {User} from '../../core/model/user.model';
import {UserService} from '../../core/service/user.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {

  constructor(private userService: UserService) {

  }

  ngOnInit() {
  }

  public onSubmit(user: User) {
    user.passwordRepeat = null;
    this.userService.post(
      {
        email: user.email,
        password: user.password
      }
    ).subscribe(
      (data: any) => {

      }
    );
  }
}
