import { IJobs } from 'app/shared/model/jobs.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IJobType {
  id?: number;
  code?: string;
  name?: string;
  jobs?: IJobs[];
  company?: ICompany;
}

export class JobType implements IJobType {
  constructor(public id?: number, public code?: string, public name?: string, public jobs?: IJobs[], public company?: ICompany) {}
}
