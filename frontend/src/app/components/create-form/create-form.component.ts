import {Component, OnInit} from '@angular/core';
import {CreateFormFormService} from "./services/create-form-form.service";
import {FormArray, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.scss']
})
export class CreateFormComponent implements OnInit {

  public form: FormGroup;

  constructor(
    private formService: CreateFormFormService
  ) {
    this.form = this.formService.buildForm();
  }

  ngOnInit(): void {
  }

  public saveForm(){
    let resForm = this.form.value;
    console.log(resForm)
  }

  get isLastChapter() {
    let chapters = <FormArray>this.form.get('chapters')
    return chapters.length == 1
  }

  public removeChapter() {
    let chapters = <FormArray>this.form.get('chapters')
    if (chapters.length > 1) {
      chapters.removeAt(chapters.length - 1)
    }
  }

  public addChapter() {
    let chapters = <FormArray>this.form.get('chapters')
    chapters.push(this.formService.createChapter(chapters.length+1))
  }
}
