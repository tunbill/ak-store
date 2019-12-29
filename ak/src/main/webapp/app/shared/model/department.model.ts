import { IEmployee } from 'app/shared/model/employee.model';

export interface IDepartment {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  employees?: IEmployee[];
}

export class Department implements IDepartment {
  constructor(public id?: number, public companyId?: number, public code?: string, public name?: string, public employees?: IEmployee[]) {}
}
