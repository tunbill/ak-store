import { IItem } from './item.model';

export interface IItemGroup {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  description?: string;
  isActive?: boolean;
  items?: IItem[];
}

export class ItemGroup implements IItemGroup {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public isActive?: boolean,
    public items?: IItem[]
  ) {
    this.isActive = this.isActive || false;
  }
}
