export class OpenQuestion {
  title: string;
  type: 'open' = 'open';
  minLength: number;
  maxLength: number;

  constructor(init: Partial<OpenQuestion>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
