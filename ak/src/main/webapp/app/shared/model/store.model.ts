import { IItem } from 'app/shared/model/item.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IStore {
  id?: number;
  code?: string;
  name?: string;
  address?: string;
  items?: IItem[];
  company?: ICompany;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public address?: string,
    public items?: IItem[],
    public company?: ICompany
  ) {}
}
