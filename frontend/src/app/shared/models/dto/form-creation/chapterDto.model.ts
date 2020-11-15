import {QuestionDto} from './questionDto.model';

export class ChapterDto {
  title: string;
  description: string;
  questions: Array<QuestionDto>;
}
