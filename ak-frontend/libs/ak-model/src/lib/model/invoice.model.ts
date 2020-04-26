import { ProcessStatus } from './enumerations';
import { IInvoiceLine } from './invoice-line.model';
import { ICustomer } from './customer.model';
import { ITerms } from './terms.model';
import { IEmployee } from './employee.model';

export interface IInvoice {
  id?: number;
  companyId?: number;
  invoiceNo?: string;
  invoiceDate?: Date;
  dueDate?: Date;
  billingAddress?: string;
  accountNumber?: string;
  poNumber?: string;
  notes?: string;
  productTotal?: number;
  vatTotal?: number;
  discountTotal?: number;
  total?: number;
  status?: ProcessStatus;
  timeCreated?: Date;
  timeModified?: Date;
  userIdCreated?: number;
  userIdModified?: number;
  invoiceLines?: IInvoiceLine[];
  customer?: ICustomer;
  terms?: ITerms;
  employee?: IEmployee;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public companyId?: number,
    public invoiceNo?: string,
    public invoiceDate?: Date,
    public dueDate?: Date,
    public billingAddress?: string,
    public accountNumber?: string,
    public poNumber?: string,
    public notes?: string,
    public productTotal?: number,
    public vatTotal?: number,
    public discountTotal?: number,
    public total?: number,
    public status?: ProcessStatus,
    public timeCreated?: Date,
    public timeModified?: Date,
    public userIdCreated?: number,
    public userIdModified?: number,
    public invoiceLines?: IInvoiceLine[],
    public customer?: ICustomer,
    public terms?: ITerms,
    public employee?: IEmployee
  ) {}
}
