import { Component, OnInit } from '@angular/core';
import {Questionnaire} from "../../shared/models/questionnaire.model";
import {AbstractControl, FormArray, FormGroup} from "@angular/forms";
import {FillFormFormService} from "./services/fill-form-form.service";
import {Question} from "../../shared/models/chapter.model";

@Component({
  selector: 'app-fill-form',
  templateUrl: './fill-form.component.html',
  styleUrls: ['./fill-form.component.scss']
})
export class FillFormComponent implements OnInit {

  public questionnaire: Questionnaire = {
    title:'form',
    chapters: [
      {
        title: 'C1',
        description: 'gfndhtyjuk',
        questions: [
          {
            title: '1?',
            type: 'oneOf',
            answers: [
              '1', '2', '3'
            ]
          }
        ]
      },
      {
        title: 'C2',
        description: '',
        questions: [
          {
            title: '??',
            type: 'open',
            minLength: 10,
            maxLength: 140
          },
          {
            title: '???',
            type: 'range',
            min: 1,
            max: 10
          },
        ]
      }
    ]
  }

  public form: FormGroup;


  constructor(
    private fillFormFormService: FillFormFormService
  ) {
    this.form = this.fillFormFormService.buildForm(this.questionnaire)
  }

  ngOnInit(): void {
  }

  get chapters() {
    return <FormArray>this.form.get('chapters');
  }

  public answers(chapter: AbstractControl) {
    return <FormArray>chapter.get('answers');
  }

  public question(chapterIdx: number, questionIdx: number) : Question {
    return this.questionnaire.chapters[chapterIdx].questions[questionIdx]
  }

  public saveForm() {
    console.log(this.form.value);
  }
}
