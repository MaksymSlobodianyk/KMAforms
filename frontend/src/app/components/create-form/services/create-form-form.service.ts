import {Injectable} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class CreateFormFormService {

  constructor(
    private formBuilder: FormBuilder
  ) { }

  public buildForm()
    : FormGroup {
    return this.formBuilder.group({
      title: ['', [Validators.required]],
      chapters: this.formBuilder.array([ this.createChapter(1)])
    });
  }

  public createChapter(number: number): FormGroup {
    return this.formBuilder.group({
      title: [`Chapter${number}`, [Validators.required]],
      description: [''],
      questions: this.formBuilder.array([], [Validators.required ,Validators.minLength(1)])
    });
  }

  public createQuestion(typeQ: string): FormGroup {
    if (typeQ === 'oneOf') {
      return this.formBuilder.group({
        title: ['', [Validators.required]],
        type: [typeQ],
        answers: this.formBuilder.array([
          ['', [Validators.required]],
          ['', [Validators.required]]
        ], [Validators.required, Validators.minLength(2)])
      });
    } else if (typeQ === 'open') {
      return this.formBuilder.group({
        title: ['', [Validators.required]],
        type: [typeQ],
        minLength :  [1, [Validators.required,Validators.min(0)]],
        maxLength: [280, [Validators.required,Validators.max(1000)]]
      });
    } else if (typeQ === 'range') {
      return this.formBuilder.group({
        title: ['', [Validators.required]],
        type: [typeQ],
        min :  [1, [Validators.required]],
        max : [10, [Validators.required]]
      });
    }
  }

  public mapToModel(form: FormGroup)
    : string {

    return "";
  }
}

