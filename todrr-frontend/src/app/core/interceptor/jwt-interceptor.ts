import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwtToken = localStorage.getItem('jwtToken');
    console.log('interceptiong request');
    if (jwtToken) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
      });

      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
