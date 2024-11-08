import { ITeacher } from 'app/shared/model/teacher.model';
import { ITimeslot } from 'app/shared/model/timeslot.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IMeeting {
  id?: number;
  meetingLink?: string;
  teacher?: ITeacher | null;
  timeslot?: ITimeslot | null;
  student?: IStudent | null;
}

export const defaultValue: Readonly<IMeeting> = {};
