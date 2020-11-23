import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {QuestionnaireWParticipants} from "../../models/questionnaire/questionnaireWParticipants.model";

@Component({
  selector: 'app-questionnaire-participants-dialog',
  templateUrl: './questionnaire-participants-dialog.component.html',
  styleUrls: ['./questionnaire-participants-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class QuestionnaireParticipantsDialogComponent implements OnInit {

  public questionnaire: QuestionnaireWParticipants
  public selectedEmail: string;

  constructor(
    private dialogRef: MatDialogRef<QuestionnaireParticipantsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.questionnaire = data
  }

  ngOnInit(): void {
  }

  public goView() {
    if (this.selectedEmail) {
      this.dialogRef.close(this.selectedEmail)
    }
  }
}
