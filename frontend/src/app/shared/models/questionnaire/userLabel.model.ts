export class UserLabel {
  displayName : string;
  email : string;

  constructor(init: Partial<UserLabel>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
