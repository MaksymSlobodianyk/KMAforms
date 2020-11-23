export class OneOfQuestion {
  id ?: string;
  title: string;
  type: 'oneOf' = 'oneOf';
  answers: Array<string>;
  options ?: string;
  answer ?: string;

  constructor(init: Partial<OneOfQuestion>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
