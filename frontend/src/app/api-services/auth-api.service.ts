import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthApiService {

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {
  }

}
