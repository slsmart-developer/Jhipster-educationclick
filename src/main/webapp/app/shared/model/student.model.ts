import dayjs from 'dayjs';

export interface IStudent {
  id?: number;
  firstName?: string;
  lastName?: string;
  contactNo?: string;
  dateOfBirth?: dayjs.Dayjs;
  gender?: string;
  email?: string;
  password?: string;
  username?: string;
  guardianName?: string | null;
  address?: string | null;
  guardianContact?: string | null;
  guardianEmail?: string | null;
}

export const defaultValue: Readonly<IStudent> = {};
