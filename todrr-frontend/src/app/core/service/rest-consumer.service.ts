import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class RestConsumerService {

  private apiResourceUrl: string;

  constructor(
    private http: HttpClient,
    private resourceName: string
  ) {
    this.apiResourceUrl = environment.apiUrl + this.resourceName + '/';
  }

  public getSingle<T>(id: number): Observable<T> {
    return this.http.get<T>(this.apiResourceUrl + id);
  }

  public add<T>(item: T): Observable<T> {
    return this.http.post<T>(this.apiResourceUrl, item);
  }
}
