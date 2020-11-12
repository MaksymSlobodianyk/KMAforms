import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormGroup} from "@angular/forms";
import {CreateFormFormService} from "../services/create-form-form.service";

@Component({
  selector: 'app-create-chapter',
  templateUrl: './create-chapter.component.html',
  styleUrls: ['./create-chapter.component.scss']
})
export class CreateChapterComponent implements OnInit {

  @Input() form: FormGroup;

  constructor(
    private formService: CreateFormFormService
  ) { }

  ngOnInit(): void {
  }

  get chapters() {
    return <FormArray>this.form.get('chapters')
  }

  public addQuestion(chapter: AbstractControl, typeQ: string) {
    let questions = chapter.get('questions') as FormArray;
    questions.push(this.formService.createQuestion(typeQ))
  }

  public deleteLastQuestion(chapter: AbstractControl) {
    let questions = chapter.get('questions') as FormArray;
    if (questions.length > 0) {
      questions.removeAt(questions.length - 1)
    }
  }

  public isNoQuestion(chapter: AbstractControl) {
    let questions = chapter.get('questions') as FormArray;
    return questions.length == 0;
  }

  public chapterIsInvalid(item: AbstractControl) {
    let questions = item.get('questions') as FormArray;
    if (questions.length == 0) { return false}
    return item.status === 'INVALID'
  }
}
