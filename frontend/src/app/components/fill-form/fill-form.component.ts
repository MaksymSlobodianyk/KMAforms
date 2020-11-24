import { Component, OnInit } from '@angular/core';
import {Questionnaire} from "../../shared/models/questionnaire/questionnaire.model";
import {AbstractControl, FormArray, FormGroup} from "@angular/forms";
import {FillFormFormService} from "./services/fill-form-form.service";
import {Question} from "../../shared/models/questionnaire/chapter.model";
import {ActivatedRoute, Router} from "@angular/router";
import {FormApiService} from "../../api-services/form-api.service";
import {Toaster} from "ngx-toast-notifications";
import {Observable} from "rxjs";

@Component({
  selector: 'app-fill-form',
  templateUrl: './fill-form.component.html',
  styleUrls: ['./fill-form.component.scss']
})
export class FillFormComponent implements OnInit {

  public userEmail: string;
  public questionnaireId : string;
  public questionnaire: Questionnaire;
  public form: FormGroup;
  public isFormReviewed = false;
  public fetchDataFunction: Observable<Questionnaire>;
  public blankViewOnly = false

  constructor(
    private fillFormFormService: FillFormFormService,
    private route: ActivatedRoute,
    private router: Router,
    private toaster: Toaster,
    private formApiService: FormApiService
  ) {
    const isFilling = this.route.snapshot.url[0].path === 'fill';
    this.questionnaireId =  this.route.snapshot.params.id;
    this.userEmail = this.route.snapshot.queryParams.user;
    this.blankViewOnly = !!this.route.snapshot.queryParams.vo;
    if (isFilling) {
      this.fetchDataFunction = this.formApiService.getForm(this.questionnaireId, this.blankViewOnly)
    }else if (this.userEmail !== undefined) {
      this.isFormReviewed = true;
      this.fetchDataFunction = this.formApiService.getAnsweredForm(this.questionnaireId, this.userEmail)
    } else {
      this.toaster.open({
        caption: '😢   Упс... Неправильно зіставлено URL',
        duration: 4000,
        type: 'warning'
      });
      this.router.navigate(['/me'])
    }
  }

  ngOnInit(): void {
    if (!this.fetchDataFunction) return
    this.fetchDataFunction.subscribe( data => {
      this.questionnaire = FillFormComponent.processGetData(data);
      this.form = this.fillFormFormService.buildForm(this.questionnaire)
      if (this.isFormReviewed || this.blankViewOnly) {
        this.form.disable();
      }
    },
    error =>{
      this.toaster.open({
        caption: FillFormComponent.handleErrorMessage(error.status),
        duration: 4000,
        type: 'warning'
      });
      this.router.navigate(['/me'])
    })
  }

  private static handleErrorMessage(errorStatus: number) : string {
    if (errorStatus === 409) {
      return '😢   Упс... Ви вже пройшли цю форму'
    } else {
      return '😢   Упс... Нам не вдалося загрузити форму'
    }
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

  public isLastChapter(chapterIdx: number) : boolean {
    return this.chapters.controls.length === chapterIdx +1
  }

  public saveForm() {
    const ids = FillFormComponent.getArrayQuestionId(this.questionnaire);
    const answers = FillFormComponent.getArrayAnswer(this.form.value)
    const res = []
    for (let i = 0; i < ids.length; i++) {
      res.push({
        ...ids[i],
        ...answers[i]
      })
    }
    this.formApiService.saveAnswers(res).subscribe(() => {
      this.toaster.open({
        caption: '🥳   Відповіді успішно збережені',
        duration: 4000,
        type: 'success'
      });
      this.router.navigate(['/me'])
    }, error => {
      this.toaster.open({
        text: error.message,
        caption: '😢   Упс... Нам не вдалося зберегти ваші відповіді',
        duration: 4000,
        type: 'warning'
      });
    })
  }

  private static getArrayQuestionId(questionnaire: Questionnaire): Array<{questionId: string}> {
    const res: Array<{questionId: string}> = [];
    questionnaire.chapters.forEach ( chapter => {
      chapter.questions.forEach (q => res.push({questionId:q.id}))
    })
    return res;
  }

  private static getArrayAnswer(formResult: any): Array<{answer: string}> {
    const res: Array<{answer: string}> = [];
    formResult.chapters.forEach ( chapter => {
      chapter.answers.forEach (ans => res.push({answer:ans}))
    })
    return res;
  }

  private static  processGetData(data: Questionnaire): Questionnaire {
    data.chapters.forEach(chapter => {
      chapter.questions = FillFormComponent.transformQuestions(chapter.questions)
    })
    return data
  }

  private static transformQuestions(questions: Array<Question>): Array<Question> {
    for (let i = 0; i < questions.length; i++) {
      let option = JSON.parse(questions[i].options)
      questions[i] = {
        ...questions[i],
        ...option
      }
    }
    return questions
  }
}
