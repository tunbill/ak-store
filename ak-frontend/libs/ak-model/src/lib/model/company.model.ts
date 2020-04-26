import { IIndustry } from './industry.model';
import { IProvince } from './province.model';

export interface ICompany {
  id?: number;
  name?: string;
  address?: string;
  logoContentType?: string;
  logo?: any;
  email?: string;
  startDate?: Date;
  numOfUsers?: number;
  type?: string;
  isActive?: boolean;
  timeCreated?: Date;
  timeModified?: Date;
  userId?: number;
  industry?: IIndustry;
  province?: IProvince;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public logoContentType?: string,
    public logo?: any,
    public email?: string,
    public startDate?: Date,
    public numOfUsers?: number,
    public type?: string,
    public isActive?: boolean,
    public timeCreated?: Date,
    public timeModified?: Date,
    public userId?: number,
    public industry?: IIndustry,
    public province?: IProvince
  ) {
    this.isActive = this.isActive || false;
  }
}
