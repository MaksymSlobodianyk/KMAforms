import {Chapter} from "./chapter.model";

export class Questionnaire {
  id ?: string;
  title: string;
  authorDisplayMame ?: string;
  authorEmail ?: string;
  chapters: Array<Chapter>;

  constructor(init: Partial<Questionnaire>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
