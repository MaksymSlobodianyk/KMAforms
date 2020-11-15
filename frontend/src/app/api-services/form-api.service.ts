import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormApiService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  saveForm(questionnaireDto: any): Observable<any> {
    return this.httpClient.post<any>(`${environment.baseURL}/api/questionnaire`, questionnaireDto);
  }

}
