import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.apiUrl + 'user';

  constructor(private httpClient: HttpClient) { }

  public post<User>(user: User): Observable<User> {
      return this.httpClient.post<User>(this.apiUrl, user);
  }
}
