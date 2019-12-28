import { Moment } from 'moment';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { ITerms } from 'app/shared/model/terms.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { ICompany } from 'app/shared/model/company.model';
import { ProcessStatus } from 'app/shared/model/enumerations/process-status.model';

export interface IInvoice {
  id?: number;
  invoiceNo?: string;
  invoiceDate?: Moment;
  dueDate?: Moment;
  billingAddress?: string;
  accountNumber?: string;
  poNumber?: string;
  notes?: string;
  productTotal?: number;
  vatTotal?: number;
  discountTotal?: number;
  total?: number;
  status?: ProcessStatus;
  timeCreated?: Moment;
  timeModified?: Moment;
  userIdCreated?: number;
  userIdModified?: number;
  invoiceLines?: IInvoiceLine[];
  customer?: ICustomer;
  terms?: ITerms;
  employee?: IEmployee;
  company?: ICompany;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public invoiceNo?: string,
    public invoiceDate?: Moment,
    public dueDate?: Moment,
    public billingAddress?: string,
    public accountNumber?: string,
    public poNumber?: string,
    public notes?: string,
    public productTotal?: number,
    public vatTotal?: number,
    public discountTotal?: number,
    public total?: number,
    public status?: ProcessStatus,
    public timeCreated?: Moment,
    public timeModified?: Moment,
    public userIdCreated?: number,
    public userIdModified?: number,
    public invoiceLines?: IInvoiceLine[],
    public customer?: ICustomer,
    public terms?: ITerms,
    public employee?: IEmployee,
    public company?: ICompany
  ) {}
}
