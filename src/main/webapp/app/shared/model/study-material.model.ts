import dayjs from 'dayjs';
import { ITeacher } from 'app/shared/model/teacher.model';

export interface IStudyMaterial {
  id?: number;
  name?: string;
  description?: string | null;
  fileContentType?: string;
  file?: string;
  uploadDate?: dayjs.Dayjs;
  teacher?: ITeacher | null;
}

export const defaultValue: Readonly<IStudyMaterial> = {};
