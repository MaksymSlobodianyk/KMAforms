import {ChapterDto} from './chapterDto.model';

export class QuestionnaireDto {
  title = '';
  chapters: Array<ChapterDto> = [];
}
