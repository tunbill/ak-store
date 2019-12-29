import { IItem } from 'app/shared/model/item.model';

export interface IStore {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  address?: string;
  items?: IItem[];
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public address?: string,
    public items?: IItem[]
  ) {}
}
