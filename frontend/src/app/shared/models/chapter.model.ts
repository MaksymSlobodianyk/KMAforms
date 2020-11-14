import {OneOfQuestion} from "./oneOfQuestion.model";
import {OpenQuestion} from "./openQuestion.model";
import {RangeQuestion} from "./rangeQuestion.model";

export class Chapter {
  title: string;
  description: string;
  questions: Array<Question>;

  constructor(init: Partial<Chapter>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}

export type Question = OneOfQuestion | OpenQuestion | RangeQuestion;
