import { IJobType } from './job-type.model';

export interface IJobs {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  status?: number;
  startDate?: Date;
  endDate?: Date;
  estimate?: number;
  investor?: string;
  address?: string;
  notes?: string;
  isActive?: boolean;
  jobType?: IJobType;
}

export class Jobs implements IJobs {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public status?: number,
    public startDate?: Date,
    public endDate?: Date,
    public estimate?: number,
    public investor?: string,
    public address?: string,
    public notes?: string,
    public isActive?: boolean,
    public jobType?: IJobType
  ) {
    this.isActive = this.isActive || false;
  }
}
