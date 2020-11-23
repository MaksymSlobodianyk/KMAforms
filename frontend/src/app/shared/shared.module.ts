import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatStepperModule} from "@angular/material/stepper";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatRadioModule} from "@angular/material/radio";
import {MatCardModule} from "@angular/material/card";
import {MatDialogModule} from "@angular/material/dialog";
import { QuestionnaireParticipantsDialogComponent } from './components/questionnaire-participants-dialog/questionnaire-participants-dialog.component';
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [QuestionnaireParticipantsDialogComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatCardModule,
    MatDialogModule,
    MatSelectModule
  ],
  exports: [
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatCardModule,
    MatDialogModule,
    MatSelectModule
  ],
  entryComponents: [QuestionnaireParticipantsDialogComponent]
})
export class SharedModule { }
