import { IInvoice } from 'app/shared/model/invoice.model';
import { IItem } from 'app/shared/model/item.model';
import { ProcessStatus } from 'app/shared/model/enumerations/process-status.model';

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
