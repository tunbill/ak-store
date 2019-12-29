import { IItem } from 'app/shared/model/item.model';

export interface IUnit {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  items?: IItem[];
}

export class Unit implements IUnit {
  constructor(public id?: number, public companyId?: number, public code?: string, public name?: string, public items?: IItem[]) {}
}
