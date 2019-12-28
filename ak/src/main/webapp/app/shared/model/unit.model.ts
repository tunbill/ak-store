import { IItem } from 'app/shared/model/item.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IUnit {
  id?: number;
  code?: string;
  name?: string;
  items?: IItem[];
  company?: ICompany;
}

export class Unit implements IUnit {
  constructor(public id?: number, public code?: string, public name?: string, public items?: IItem[], public company?: ICompany) {}
}
