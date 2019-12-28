import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IDepartment } from 'app/shared/model/department.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IEmployee {
  id?: number;
  code?: string;
  fullName?: string;
  sex?: number;
  birthday?: Moment;
  identityCard?: string;
  identityDate?: Moment;
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
  timeCreated?: Moment;
  timeModified?: Moment;
  userIdCreated?: number;
  userIdModified?: number;
  invoices?: IInvoice[];
  department?: IDepartment;
  company?: ICompany;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public code?: string,
    public fullName?: string,
    public sex?: number,
    public birthday?: Moment,
    public identityCard?: string,
    public identityDate?: Moment,
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
    public timeCreated?: Moment,
    public timeModified?: Moment,
    public userIdCreated?: number,
    public userIdModified?: number,
    public invoices?: IInvoice[],
    public department?: IDepartment,
    public company?: ICompany
  ) {
    this.isActive = this.isActive || false;
  }
}
