import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Questionnaire} from '../shared/models/questionnaire/questionnaire.model';
import {Answer} from '../shared/models/questionnaire/answer.model';
import {QuestionnaireWParticipants} from '../shared/models/questionnaire/questionnaireWParticipants.model';
import {User} from '../shared/models/auth/user';

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

  public getForm(id: string, vo: boolean = false): Observable<Questionnaire> {
    return this.httpClient
      .get<Questionnaire>(`${environment.baseURL}/api/questionnaire?questionnaireId=${id}&vo=${vo}`);
  }

  public saveAnswers(answers: Array<Answer>): Observable<any> {
    return this.httpClient.post<any>(`${environment.baseURL}/api/answer`, answers);
  }

  public getAnsweredForm(questionnaireId: string, email: string): Observable<Questionnaire> {
    return this.httpClient
      .get<Questionnaire>(
        `${environment.baseURL}/api/answer/user?questionnaireId=${questionnaireId}&userEmail=${email}`
      );
  }

  public getMyQuestionnairesWParticipants(): Observable<Array<QuestionnaireWParticipants>> {
    return this.httpClient
      .get<Array<QuestionnaireWParticipants>>(`${environment.baseURL}/api/questionnaire/all/detail`);
  }

  public getMyQuestionnaires(): Observable<Array<Questionnaire>> {
    return this.httpClient
      .get<Array<Questionnaire>>(`${environment.baseURL}/api/questionnaire/all`);
  }

  public getAllActiveQuestionnaires(): Observable<Array<Questionnaire>> {
    return this.httpClient
      .get<Array<Questionnaire>>(`${environment.baseURL}/api/questionnaire/all-t`);
  }

  public enableQuestionnaire(questionnaireId: string): Observable<any> {
    return this.httpClient.post<any>(`${environment.baseURL}/api/questionnaire/enable?questionnaireId=${questionnaireId}`, {});
  }

  public disableQuestionnaire(questionnaireId: string): Observable<any> {
    return this.httpClient.post<any>(`${environment.baseURL}/api/questionnaire/disable?questionnaireId=${questionnaireId}`, {});
  }

  public deleteQuestionnaire(questionnaireId: string): Observable<any> {
    return this.httpClient.delete(`${environment.baseURL}/api/questionnaire?questionnaireId=${questionnaireId}`);
  }

  public changeRole(makeAdmin: boolean, email: string): any {
    return this.httpClient
      .post<any>(`${environment.baseURL}/api/user/role?makeAdmin=${makeAdmin}&userEmail=${email}`, {});
  }

  public getAllUsers(): Observable<Array<User>> {
    return this.httpClient.get<Array<User>>(`${environment.baseURL}/api/user/all`);
  }

  public getStatisticForQuestionnaire(questionnaireId: string): Observable<Questionnaire> {
    return this.httpClient
      .get<Questionnaire>(`${environment.baseURL}/api/answer/statistic?questionnaireId=${questionnaireId}`);
  }

  public checkIfAdmin(): Observable<boolean> {
    return this.httpClient.get<boolean>(`${environment.baseURL}/api/user/admin`);
  }
}
