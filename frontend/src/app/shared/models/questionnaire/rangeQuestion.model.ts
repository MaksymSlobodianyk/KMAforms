export class RangeQuestion {
  id ?: string;
  title: string;
  type: 'range' = 'range';
  min: number;
  max: number;
  options ?: string;

  constructor(init: Partial<RangeQuestion>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
