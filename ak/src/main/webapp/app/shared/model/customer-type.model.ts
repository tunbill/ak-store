import { ICustomer } from 'app/shared/model/customer.model';
import { ICompany } from 'app/shared/model/company.model';

export interface ICustomerType {
  id?: number;
  name?: string;
  description?: string;
  isActive?: boolean;
  customers?: ICustomer[];
  company?: ICompany;
}

export class CustomerType implements ICustomerType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public isActive?: boolean,
    public customers?: ICustomer[],
    public company?: ICompany
  ) {
    this.isActive = this.isActive || false;
  }
}
