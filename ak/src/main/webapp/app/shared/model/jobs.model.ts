import { Moment } from 'moment';
import { IJobType } from 'app/shared/model/job-type.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IJobs {
  id?: number;
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
  company?: ICompany;
}

export class Jobs implements IJobs {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public status?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public estimate?: number,
    public investor?: string,
    public address?: string,
    public notes?: string,
    public jobType?: IJobType,
    public company?: ICompany
  ) {}
}
