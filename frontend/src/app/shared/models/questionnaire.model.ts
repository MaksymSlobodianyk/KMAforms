import {Chapter} from "./chapter.model";

export class Questionnaire {
  title: string;
  chapters: Array<Chapter>;

  constructor(init: Partial<Questionnaire>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
