export class RangeQuestion {
  title: string;
  type: 'range' = 'range';
  min: number;
  max: number;

  constructor(init: Partial<RangeQuestion>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
