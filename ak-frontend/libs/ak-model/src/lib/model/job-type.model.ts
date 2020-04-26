import { IJobs } from './jobs.model';

export interface IJobType {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  description?: string;
  isActive?: boolean;
  jobs?: IJobs[];
}

export class JobType implements IJobType {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public isActive?: boolean,
    public jobs?: IJobs[]
  ) {
    this.isActive = this.isActive || false;
  }
}
