import {Component, OnInit} from '@angular/core';
import {CreateFormFormService} from './services/create-form-form.service';
import {FormArray, FormGroup} from '@angular/forms';
import {FormApiService} from '../../api-services/form-api.service';
import {Toaster} from 'ngx-toast-notifications';

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.scss']
})
export class CreateFormComponent implements OnInit {

  public form: FormGroup;

  constructor(
    private formService: CreateFormFormService,
    private formApiService: FormApiService,
    private toaster: Toaster
  ) {
    this.form = this.formService.buildForm();
  }

  ngOnInit(): void {
  }

  public saveForm(): void {
    this.formApiService.saveForm(this.formService.mapQuestionnaireToDto(this.form.value)).subscribe(response => {
      this.toaster.open({
        caption: '🥳   Форма успішно збережена',
        duration: 4000,
        type: 'success'
      });
    }, error => {
      this.toaster.open({
        text: error.message,
        caption: '😢   Упс... Нам не вдалося зберегти вашу форму',
        duration: 4000,
        type: 'warning'
      });
    });
  }

  get isLastChapter(): boolean {
    const chapters = this.form.get('chapters') as FormArray;
    return chapters.length === 1;
  }

  public removeChapter(): void {
    const chapters = this.form.get('chapters') as FormArray;
    if (chapters.length > 1) {
      chapters.removeAt(chapters.length - 1);
    }
  }

  public addChapter(): void {
    const chapters = this.form.get('chapters') as FormArray;
    chapters.push(this.formService.createChapter(chapters.length + 1));
  }
}
