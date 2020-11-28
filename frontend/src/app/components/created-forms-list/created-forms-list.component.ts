import {Component, OnInit} from '@angular/core';
import {Questionnaire} from '../../shared/models/questionnaire/questionnaire.model';
import {formatDate} from '../../shared/helpers/format-functions';
import {FormApiService} from '../../api-services/form-api.service';
import {Toaster} from 'ngx-toast-notifications';
import {Router} from '@angular/router';
import {MatSlideToggleChange} from '@angular/material/slide-toggle';

@Component({
  selector: 'app-created-forms-list',
  templateUrl: './created-forms-list.component.html',
  styleUrls: ['./created-forms-list.component.scss']
})
export class CreatedFormsListComponent implements OnInit {

  public questionnaires: Array<Questionnaire>;

  constructor(
    private formApiService: FormApiService,
    private toaster: Toaster,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.formApiService.getMyQuestionnaires().subscribe(data => {
      this.questionnaires = data;
    }, error => {
      this.toaster.open({
        caption: '😢   Упс... Нам не вдалося загрузити форми',
        duration: 4000,
        type: 'warning'
      });
      this.router.navigate(['/me']);
    });
  }

  public labelAction(isActive: boolean) {
    return isActive ? 'Deactivate' : 'Activate';
  }

  public showDate(questionnaire: Questionnaire) {
    return formatDate(new Date(questionnaire.createdAt));
  }

  public changeActivation($event: MatSlideToggleChange, quest: Questionnaire) {
    const newValue = $event.checked;
    const func = newValue ?
      this.formApiService.enableQuestionnaire(quest.id) : this.formApiService.disableQuestionnaire(quest.id);
    func.subscribe(_ => {
        this.toaster.open({
          caption: 'Cтатус змінено',
          duration: 4000,
          type: 'success'
        });
        quest.activated = newValue;
      },
      error => {
        this.toaster.open({
          caption: `😢   ${error.error.message}`,
          duration: 4000,
          type: 'warning'
        });
      });
  }

  public reviewQuest(id: string) {
    this.router.navigate(['/me', 'review', id], {queryParams: {vo: 1}});
  }

  public deleteQuest(id: string) {
    if (confirm('Are you sure you want to delete this questionnaire?')) {
      this.formApiService.deleteQuestionnaire(id).subscribe(_ => {
        this.toaster.open({
          caption: 'Опитник видалено',
          duration: 4000,
          type: 'success'
        });
        this.questionnaires = this.questionnaires.filter(q => q.id !== id);
      }, error => {
        this.toaster.open({
          caption: `😢   ${error.error.message}`,
          duration: 4000,
          type: 'warning'
        });
      });
    }
  }

  public shareLink(id: string) {
    const baseUrl = 'http://localhost:4200';
    const url = `${baseUrl}/me/fill/${id}`;
    navigator.clipboard.writeText(url).then(() => {
      this.toaster.open({
        caption: 'URL saved to clipboard',
        duration: 4000,
        type: 'success'
      });
    });
  }

  public goViewStat(id: string) {
    this.router.navigate(['/me', 'statistics', id]);
  }
}
