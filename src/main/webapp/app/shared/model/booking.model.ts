import dayjs from 'dayjs';
import { ITimeslot } from 'app/shared/model/timeslot.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IBooking {
  id?: number;
  bookingDate?: dayjs.Dayjs;
  payment?: number;
  timeslot?: ITimeslot | null;
  teacher?: ITeacher | null;
  student?: IStudent | null;
}

export const defaultValue: Readonly<IBooking> = {};
