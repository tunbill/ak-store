import { ICustomer } from './customer.model';
import { IInvoice } from './invoice.model';

export interface ITerms {
  id?: number;
  code?: string;
  name?: string;
  dayOfMonthDue?: number;
  dueNextMonthDays?: number;
  discountDayOfMonth?: number;
  discountPct?: number;
  customers?: ICustomer[];
  invoices?: IInvoice[];
}

export class Terms implements ITerms {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public dayOfMonthDue?: number,
    public dueNextMonthDays?: number,
    public discountDayOfMonth?: number,
    public discountPct?: number,
    public customers?: ICustomer[],
    public invoices?: IInvoice[]
  ) {}
}
