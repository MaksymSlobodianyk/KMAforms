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
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatListModule} from "@angular/material/list";
import {MatDividerModule} from "@angular/material/divider";

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
    MatSelectModule,
    MatSlideToggleModule,
    MatListModule,
    MatDividerModule
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
    MatSelectModule,
    MatSlideToggleModule,
    MatListModule,
    MatDividerModule
  ],
  entryComponents: [QuestionnaireParticipantsDialogComponent]
})
export class SharedModule { }
