export class OneOfQuestion {
  title: string;
  type: 'oneOf' = 'oneOf';
  answers: Array<string>;

  constructor(init: Partial<OneOfQuestion>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
