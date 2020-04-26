import { ICompany } from '@akfe/shared/model/company.model';

export interface IProvince {
  id?: number;
  name?: string;
  companies?: ICompany[];
}

export class Province implements IProvince {
  constructor(public id?: number, public name?: string, public companies?: ICompany[]) {}
}
