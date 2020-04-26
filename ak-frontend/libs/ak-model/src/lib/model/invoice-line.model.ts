import { ProcessStatus } from './enumerations';
import { IInvoice } from './invoice.model';
import { IItem } from './item.model';

export interface IInvoiceLine {
  id?: number;
  companyId?: number;
  displayOrder?: number;
  itemName?: string;
  unitName?: string;
  quantity?: number;
  rate?: number;
  amount?: number;
  discountPct?: number;
  accountNumber?: string;
  status?: ProcessStatus;
  invoice?: IInvoice;
  item?: IItem;
}

export class InvoiceLine implements IInvoiceLine {
  constructor(
    public id?: number,
    public companyId?: number,
    public displayOrder?: number,
    public itemName?: string,
    public unitName?: string,
    public quantity?: number,
    public rate?: number,
    public amount?: number,
    public discountPct?: number,
    public accountNumber?: string,
    public status?: ProcessStatus,
    public invoice?: IInvoice,
    public item?: IItem
  ) {}
}
