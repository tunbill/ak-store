import { IInvoice } from './invoice.model';
import { IDepartment } from './department.model';

export interface IEmployee {
  id?: number;
  companyId?: number;
  code?: string;
  fullName?: string;
  sex?: number;
  birthday?: Date;
  identityCard?: string;
  identityDate?: Date;
  identityIssue?: string;
  position?: string;
  taxCode?: string;
  salary?: number;
  salaryRate?: number;
  salarySecurity?: number;
  numOfDepends?: number;
  phone?: string;
  mobile?: string;
  email?: string;
  bankAccount?: string;
  bankName?: string;
  nodes?: string;
  isActive?: boolean;
  timeCreated?: Date;
  timeModified?: Date;
  userIdCreated?: number;
  userIdModified?: number;
  invoices?: IInvoice[];
  department?: IDepartment;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public fullName?: string,
    public sex?: number,
    public birthday?: Date,
    public identityCard?: string,
    public identityDate?: Date,
    public identityIssue?: string,
    public position?: string,
    public taxCode?: string,
    public salary?: number,
    public salaryRate?: number,
    public salarySecurity?: number,
    public numOfDepends?: number,
    public phone?: string,
    public mobile?: string,
    public email?: string,
    public bankAccount?: string,
    public bankName?: string,
    public nodes?: string,
    public isActive?: boolean,
    public timeCreated?: Date,
    public timeModified?: Date,
    public userIdCreated?: number,
    public userIdModified?: number,
    public invoices?: IInvoice[],
    public department?: IDepartment
  ) {
    this.isActive = this.isActive || false;
  }
}
