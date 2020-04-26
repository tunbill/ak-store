import { IItem } from './item.model';

export interface IStore {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  description?: string;
  address?: string;
  isActive?: boolean;
  items?: IItem[];
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public address?: string,
    public isActive?: boolean,
    public items?: IItem[]
  ) {
    this.isActive = this.isActive || false;
  }
}
