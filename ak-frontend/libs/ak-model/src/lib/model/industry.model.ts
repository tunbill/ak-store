import { ICompany } from './company.model';

export interface IIndustry {
  id?: number;
  name?: string;
  companies?: ICompany[];
}

export class Industry implements IIndustry {
  constructor(
    public id?: number,
    public name?: string,
    public companies?: ICompany[]
  ) {}
}
