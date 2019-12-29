import { IJobs } from 'app/shared/model/jobs.model';

export interface IJobType {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  jobs?: IJobs[];
}

export class JobType implements IJobType {
  constructor(public id?: number, public companyId?: number, public code?: string, public name?: string, public jobs?: IJobs[]) {}
}
