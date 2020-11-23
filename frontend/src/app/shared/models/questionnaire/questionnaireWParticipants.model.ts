import {UserLabel} from "./userLabel.model";

export class QuestionnaireWParticipants {
  id : string;
  title: string;
  authorDisplayMame : string;
  authorEmail : string;
  createdAt: string;
  activated: boolean;
  participants: Array<UserLabel>;

  constructor(init: Partial<QuestionnaireWParticipants>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
