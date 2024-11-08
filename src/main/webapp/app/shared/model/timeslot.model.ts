import dayjs from 'dayjs';
import { ITeacher } from 'app/shared/model/teacher.model';

export interface ITimeslot {
  id?: number;
  startTime?: dayjs.Dayjs;
  endTime?: dayjs.Dayjs;
  availability?: boolean;
  teacher?: ITeacher | null;
}

export const defaultValue: Readonly<ITimeslot> = {
  availability: false,
};
