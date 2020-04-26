import { IInvoice } from './invoice.model';
import { ICustomerType } from './customer-type.model';
import { ITerms } from './terms.model';

export interface ICustomer {
  id?: number;
  companyId?: number;
  isVendor?: boolean;
  vendorId?: number;
  code?: string;
  companyName?: string;
  address?: string;
  phone?: string;
  mobile?: string;
  fax?: string;
  email?: string;
  website?: string;
  taxCode?: string;
  accountNumber?: string;
  bankAccount?: string;
  bankName?: string;
  balance?: number;
  totalBalance?: number;
  openBalance?: number;
  openBalanceDate?: Date;
  creditLimit?: number;
  notes?: string;
  contactName?: string;
  contactMobile?: string;
  contactEmail?: string;
  isActive?: boolean;
  timeCreated?: Date;
  timeModified?: Date;
  userIdCreated?: number;
  userIdModified?: number;
  invoices?: IInvoice[];
  customerType?: ICustomerType;
  terms?: ITerms;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public companyId?: number,
    public isVendor?: boolean,
    public vendorId?: number,
    public code?: string,
    public companyName?: string,
    public address?: string,
    public phone?: string,
    public mobile?: string,
    public fax?: string,
    public email?: string,
    public website?: string,
    public taxCode?: string,
    public accountNumber?: string,
    public bankAccount?: string,
    public bankName?: string,
    public balance?: number,
    public totalBalance?: number,
    public openBalance?: number,
    public openBalanceDate?: Date,
    public creditLimit?: number,
    public notes?: string,
    public contactName?: string,
    public contactMobile?: string,
    public contactEmail?: string,
    public isActive?: boolean,
    public timeCreated?: Date,
    public timeModified?: Date,
    public userIdCreated?: number,
    public userIdModified?: number,
    public invoices?: IInvoice[],
    public customerType?: ICustomerType,
    public terms?: ITerms
  ) {
    this.isVendor = this.isVendor || false;
    this.isActive = this.isActive || false;
  }
}
