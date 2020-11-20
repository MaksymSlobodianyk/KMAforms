import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Questionnaire} from "../shared/models/questionnaire/questionnaire.model";
import {Answer} from "../shared/models/questionnaire/answer.model";

@Injectable({
  providedIn: 'root'
})
export class FormApiService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  public saveForm(questionnaireDto: any): Observable<any> {
    return this.httpClient.post<any>(`${environment.baseURL}/api/questionnaire`, questionnaireDto);
  }

  public getForm(id: string) : Observable<Questionnaire> {
    return this.httpClient.get<Questionnaire>(`${environment.baseURL}/api/questionnaire?questionnaireId=${id}`);
  }

  public saveAnswers(answers: Array<Answer>): Observable<any> {
    return this.httpClient.post<any>(`${environment.baseURL}/api/answer`, answers);
  }
}
