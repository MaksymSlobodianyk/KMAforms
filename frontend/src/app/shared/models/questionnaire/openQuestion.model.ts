export class OpenQuestion {
  id ?: string;
  title: string;
  type: 'open' = 'open';
  minLength: number;
  maxLength: number;
  options ?: string;
  answer ?: string;

  constructor(init: Partial<OpenQuestion>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
