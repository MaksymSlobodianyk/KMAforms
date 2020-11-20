export class Answer {
  questionId : string;
  answer: string;

  constructor(init: Partial<Answer>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
