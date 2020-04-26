import { Moment } from 'moment';
import { IIndustry } from '@akfe/shared/model/industry.model';
import { IProvince } from '@akfe/shared/model/province.model';

export interface ICompany {
  id?: number;
  name?: string;
  address?: string;
  logoContentType?: string;
  logo?: any;
  email?: string;
  startDate?: Moment;
  numOfUsers?: number;
  type?: string;
  isActive?: boolean;
  timeCreated?: Moment;
  timeModified?: Moment;
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
    public startDate?: Moment,
    public numOfUsers?: number,
    public type?: string,
    public isActive?: boolean,
    public timeCreated?: Moment,
    public timeModified?: Moment,
    public userId?: number,
    public industry?: IIndustry,
    public province?: IProvince
  ) {
    this.isActive = this.isActive || false;
  }
}
