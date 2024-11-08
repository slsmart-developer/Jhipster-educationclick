import { ISubject } from 'app/shared/model/subject.model';

export interface ITeacher {
  id?: number;
  name?: string;
  email?: string;
  feePerHour?: number;
  subject?: ISubject | null;
}

export const defaultValue: Readonly<ITeacher> = {};
