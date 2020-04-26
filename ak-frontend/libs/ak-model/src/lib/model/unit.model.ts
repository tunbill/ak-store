import { IItem } from './item.model';

export interface IUnit {
  id?: number;
  companyId?: number;
  name?: string;
  description?: string;
  isActive?: boolean;
  items?: IItem[];
}

export class Unit implements IUnit {
  constructor(
    public id?: number,
    public companyId?: number,
    public name?: string,
    public description?: string,
    public isActive?: boolean,
    public items?: IItem[]
  ) {
    this.isActive = this.isActive || false;
  }
}
