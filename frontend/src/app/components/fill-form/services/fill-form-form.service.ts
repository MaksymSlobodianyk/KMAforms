import {Injectable} from "@angular/core";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Questionnaire} from "../../../shared/models/questionnaire.model";
import {Chapter} from "../../../shared/models/chapter.model";

@Injectable({
  providedIn: 'root'
})
export class FillFormFormService {

  constructor(
    private formBuilder: FormBuilder
  ) { }

  public buildForm(questionnaire: Questionnaire)
    : FormGroup {
    return this.formBuilder.group({
      chapters: this.configureChapters(questionnaire.chapters)
    });
  }

  private configureChapters(chapters: Array<Chapter>) {
    let res = this.formBuilder.array([]);
    chapters.forEach(chapter => {
      let chapterFA = this.formBuilder.array([]);

      chapter.questions.forEach(question => {
        if (question.type === 'oneOf') {
          chapterFA.push(new FormControl(null, [Validators.required]))
        } else if (question.type === 'open') {
          chapterFA.push(new FormControl(null, [Validators.required,
            Validators.minLength(question.minLength), Validators.maxLength(question.maxLength)]))
        } else if (question.type === 'range') {
          chapterFA.push(new FormControl(question.min, [Validators.required,
            Validators.min(question.min), Validators.max(question.max)]))
        }
      })

      res.push(this.formBuilder.group({
        answers: chapterFA
      }))
    })

    return res
  }


  public mapToModel(form: FormGroup)
    : string {

    return "";

  }

}

