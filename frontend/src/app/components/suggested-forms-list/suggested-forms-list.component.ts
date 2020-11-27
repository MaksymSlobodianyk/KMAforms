import { Component, OnInit } from '@angular/core';
import {FormApiService} from "../../api-services/form-api.service";
import {Toaster} from "ngx-toast-notifications";
import {Router} from "@angular/router";
import {Questionnaire} from "../../shared/models/questionnaire/questionnaire.model";
import {formatDate} from "../../shared/helpers/format-functions";

@Component({
  selector: 'app-sujested-forms-list',
  templateUrl: './suggested-forms-list.component.html',
  styleUrls: ['./suggested-forms-list.component.scss']
})
export class SuggestedFormsListComponent implements OnInit {

  public questionnaires: Array<Questionnaire>

  constructor(
    private formApiService: FormApiService,
    private toaster: Toaster,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.formApiService.getAllActiveQuestionnaires().subscribe( data => {
      this.questionnaires = data;
    }, error => {
      this.toaster.open({
        caption: '😢   Упс... Нам не вдалося загрузити форми',
        duration: 4000,
        type: 'warning'
      });
      this.router.navigate(['/me'])
    })
  }


  public showDate(questionnaire:Questionnaire) {
    return formatDate(new Date(questionnaire.createdAt));
  }

  public goFillQ(id: string) {
    this.router.navigate(['me', 'fill', id])
  }
}
