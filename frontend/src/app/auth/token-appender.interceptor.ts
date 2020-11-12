import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {AuthService} from './auth.service';

@Injectable()
export class TokenAppenderInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
  ) {
  }

  public intercept(request: HttpRequest<any>, next: HttpHandler)
    : Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authService.idToken}`,
        'Content-Type': 'application/json',
        'Accept-Language': 'ua'
      }
    });
    return next.handle(request);
  }
}
