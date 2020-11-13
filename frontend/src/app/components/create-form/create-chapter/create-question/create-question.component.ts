import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.scss']
})
export class CreateQuestionComponent implements OnInit {

  @Input("chapter") item: FormGroup;

  constructor() { }

  ngOnInit(): void {
  }

  public questions(chapter: AbstractControl) {
    return <FormArray>chapter.get('questions')
  }

  public answers(question: AbstractControl) {
    return <FormArray>question.get('answers')
  }

  public addAnswer(question: AbstractControl) {
    let answers = question.get('answers') as FormArray
    answers.push(new FormControl('', [Validators.required]))
  }

  public removeAnswer(question: AbstractControl) {
    let answerss = question.get('answers') as FormArray
    if (answerss.length > 2) {
      answerss.removeAt(answerss.length-1)
    }
  }

  public invalidPrevAnswers(question: AbstractControl) {
    let answers = question.get('answers') as FormArray
    return answers.status === 'INVALID';
  }

  public only2Answers(question: AbstractControl) {
    let answers = question.get('answers') as FormArray
    return answers.length === 2;
  }
}
