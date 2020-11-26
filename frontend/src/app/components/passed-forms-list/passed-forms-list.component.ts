import { Component, OnInit } from '@angular/core';
import {FormApiService} from "../../api-services/form-api.service";
import {QuestionnaireWParticipants} from "../../shared/models/questionnaire/questionnaireWParticipants.model";
import {Toaster} from "ngx-toast-notifications";
import {Router} from "@angular/router";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {QuestionnaireParticipantsDialogComponent} from "../../shared/components/questionnaire-participants-dialog/questionnaire-participants-dialog.component";

@Component({
  selector: 'app-passed-forms-list',
  templateUrl: './passed-forms-list.component.html',
  styleUrls: ['./passed-forms-list.component.scss']
})
export class PassedFormsListComponent implements OnInit {

  public questionnaires: Array<QuestionnaireWParticipants>

  constructor(
    private formApiService: FormApiService,
    private toaster: Toaster,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.formApiService.getMyQuestionnairesWParticipants()
      .subscribe(data => {
        this.questionnaires = data
      },
        error => {
          this.toaster.open({
            caption: '😢   Упс... Нам не вдалося загрузити форми',
            duration: 4000,
            type: 'warning'
          });
          this.router.navigate(['/me'])
        });
  }

  public showDate(questionnaire: QuestionnaireWParticipants): string {
    return PassedFormsListComponent.formatDate(new Date(questionnaire.createdAt));
  }

  public openDialog(questionnaire: QuestionnaireWParticipants) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.data = questionnaire;
    dialogConfig.width = "400px";

    const dialogRef = this.dialog.open(QuestionnaireParticipantsDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      email => {
        if (email) {
          this.router.navigate(['/me', 'review', questionnaire.id], {queryParams: {user: email}})
        }
      });
  }

  private static formatDate(date: Date): string {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [day, month, year].join('/');
  }

}
