import { Moment } from 'moment';
import { ICustomerType } from 'app/shared/model/customer-type.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IStore } from 'app/shared/model/store.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IJobs } from 'app/shared/model/jobs.model';
import { IJobType } from 'app/shared/model/job-type.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IItem } from 'app/shared/model/item.model';
import { IItemGroup } from 'app/shared/model/item-group.model';
import { IUnit } from 'app/shared/model/unit.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { IIndustry } from 'app/shared/model/industry.model';
import { IProvince } from 'app/shared/model/province.model';

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
  customerTypes?: ICustomerType[];
  customers?: ICustomer[];
  stores?: IStore[];
  departments?: IDepartment[];
  jobs?: IJobs[];
  jobTypes?: IJobType[];
  employees?: IEmployee[];
  items?: IItem[];
  itemGroups?: IItemGroup[];
  units?: IUnit[];
  invoices?: IInvoice[];
  invoiceLines?: IInvoiceLine[];
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
    public customerTypes?: ICustomerType[],
    public customers?: ICustomer[],
    public stores?: IStore[],
    public departments?: IDepartment[],
    public jobs?: IJobs[],
    public jobTypes?: IJobType[],
    public employees?: IEmployee[],
    public items?: IItem[],
    public itemGroups?: IItemGroup[],
    public units?: IUnit[],
    public invoices?: IInvoice[],
    public invoiceLines?: IInvoiceLine[],
    public industry?: IIndustry,
    public province?: IProvince
  ) {
    this.isActive = this.isActive || false;
  }
}
