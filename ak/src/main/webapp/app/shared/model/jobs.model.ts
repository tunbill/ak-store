import { Moment } from 'moment';
import { IJobType } from 'app/shared/model/job-type.model';

export interface IJobs {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  status?: number;
  startDate?: Moment;
  endDate?: Moment;
  estimate?: number;
  investor?: string;
  address?: string;
  notes?: string;
  jobType?: IJobType;
}

export class Jobs implements IJobs {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public status?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public estimate?: number,
    public investor?: string,
    public address?: string,
    public notes?: string,
    public jobType?: IJobType
  ) {}
}
