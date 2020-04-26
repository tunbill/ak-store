import { IEmployee } from './employee.model';

export interface IDepartment {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  description?: string;
  isActive?: boolean;
  employees?: IEmployee[];
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public isActive?: boolean,
    public employees?: IEmployee[]
  ) {
    this.isActive = this.isActive || false;
  }
}
