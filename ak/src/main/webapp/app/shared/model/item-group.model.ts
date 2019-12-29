import { IItem } from 'app/shared/model/item.model';

export interface IItemGroup {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  items?: IItem[];
}

export class ItemGroup implements IItemGroup {
  constructor(public id?: number, public companyId?: number, public code?: string, public name?: string, public items?: IItem[]) {}
}
