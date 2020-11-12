import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {User} from '../models/auth/user';

@Injectable({
  providedIn: 'root'
})
export class AuthApiService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  authenticate(): Promise<User> {
    const params = new HttpParams();
    return this.httpClient.post<User>(`${environment.baseURL}/api/register`, {params}).toPromise();
  }

}
