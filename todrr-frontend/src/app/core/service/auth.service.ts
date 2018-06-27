import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.apiUrl + 'auth/';

  constructor(
    private http: HttpClient
  ) { }

  public logIn(email: string, password: string): void {
    console.log(email + password);
    this.http.post<User>(this.apiUrl + "login", {email, password})
      .subscribe(
        (response: any) => {this.setJwtAuthentication(response.jwtToken)}
      )
  }

  public isAuthenticated(): boolean {
    if(localStorage.getItem("jwtToken") == null || localStorage.getItem("jwtToken") === ""){
        return false;
    }
    return true;
  }

  private setJwtAuthentication(jwtToken: string): void{
    console.log(jwtToken);
    localStorage.setItem("jwtToken", jwtToken);
  }
}
