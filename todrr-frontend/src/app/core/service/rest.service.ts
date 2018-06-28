import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient
  ) { }

  public getOne<T>(resourceUrl: string, resourceId: any): Observable<T> {
    return this.http.get<T>(this.apiUrl + resourceUrl + '/' + resourceId);
  }

  public post<T>(resourceUrl: string, payload: T): Observable<T> {
    return this.http.post<T>(this.apiUrl + resourceUrl, payload);
  }
}
