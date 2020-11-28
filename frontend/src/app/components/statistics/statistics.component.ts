import {Component, OnInit} from '@angular/core';
import {FormApiService} from '../../api-services/form-api.service';
import {Toaster} from 'ngx-toast-notifications';
import {Questionnaire} from '../../shared/models/questionnaire/questionnaire.model';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

  public questionnaire: Questionnaire;

  constructor(
    private formApiService: FormApiService,
    private toaster: Toaster,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.formApiService.getStatisticForQuestionnaire(this.route.snapshot.params.id).subscribe(data => {
      this.questionnaire = data;
      console.log(this.questionnaire);
    }, error => {
      this.toaster.open({
        caption: '😢   Упс... Нам не вдалося сформувати статистику',
        duration: 4000,
        type: 'warning'
      });
    });
  }

}
