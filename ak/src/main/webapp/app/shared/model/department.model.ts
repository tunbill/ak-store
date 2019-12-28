import { IEmployee } from 'app/shared/model/employee.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IDepartment {
  id?: number;
  code?: string;
  name?: string;
  employees?: IEmployee[];
  company?: ICompany;
}

export class Department implements IDepartment {
  constructor(public id?: number, public code?: string, public name?: string, public employees?: IEmployee[], public company?: ICompany) {}
}
