import { ICustomer } from './customer.model';

export interface ICustomerType {
  id?: number;
  companyId?: number;
  name?: string;
  description?: string;
  isActive?: boolean;
  customers?: ICustomer[];
}

export class CustomerType implements ICustomerType {
  constructor(
    public id?: number,
    public companyId?: number,
    public name?: string,
    public description?: string,
    public isActive?: boolean,
    public customers?: ICustomer[]
  ) {
    this.isActive = this.isActive || false;
  }
}
