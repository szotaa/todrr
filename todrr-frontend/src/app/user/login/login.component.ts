import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../core/service/auth.service";
import {User} from "../../core/model/user.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  public onSubmit(user: User){
    this.authService.logIn(user.email, user.password);
    if(this.authService.isAuthenticated()){
      console.log("login success");
    }
  }
}
