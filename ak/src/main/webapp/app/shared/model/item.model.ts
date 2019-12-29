import { Moment } from 'moment';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { IUnit } from 'app/shared/model/unit.model';
import { IItemGroup } from 'app/shared/model/item-group.model';
import { IStore } from 'app/shared/model/store.model';
import { ItemType } from 'app/shared/model/enumerations/item-type.model';
import { VATTax } from 'app/shared/model/enumerations/vat-tax.model';
import { PriceMethod } from 'app/shared/model/enumerations/price-method.model';

export interface IItem {
  id?: number;
  companyId?: number;
  code?: string;
  name?: string;
  description?: string;
  type?: ItemType;
  skuNum?: number;
  vatTax?: VATTax;
  importTax?: number;
  exportTax?: number;
  inventoryPriceMethod?: PriceMethod;
  accountItem?: string;
  isAllowModified?: boolean;
  accountCost?: string;
  accountRevenue?: string;
  accountInternalRevenue?: string;
  accountSaleReturn?: string;
  accountSalePrice?: string;
  accountAgency?: string;
  accountRawProduct?: string;
  accountCostDifference?: string;
  accountDiscount?: string;
  saleDesc?: string;
  purchaseDesc?: string;
  weight?: number;
  lenght?: number;
  wide?: number;
  height?: number;
  color?: string;
  specification?: string;
  itemImageContentType?: string;
  itemImage?: any;
  isActive?: boolean;
  timeCreated?: Moment;
  timeModified?: Moment;
  userIdCreated?: number;
  userIdModified?: number;
  invoiceLines?: IInvoiceLine[];
  unit?: IUnit;
  itemGroup?: IItemGroup;
  store?: IStore;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public companyId?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public type?: ItemType,
    public skuNum?: number,
    public vatTax?: VATTax,
    public importTax?: number,
    public exportTax?: number,
    public inventoryPriceMethod?: PriceMethod,
    public accountItem?: string,
    public isAllowModified?: boolean,
    public accountCost?: string,
    public accountRevenue?: string,
    public accountInternalRevenue?: string,
    public accountSaleReturn?: string,
    public accountSalePrice?: string,
    public accountAgency?: string,
    public accountRawProduct?: string,
    public accountCostDifference?: string,
    public accountDiscount?: string,
    public saleDesc?: string,
    public purchaseDesc?: string,
    public weight?: number,
    public lenght?: number,
    public wide?: number,
    public height?: number,
    public color?: string,
    public specification?: string,
    public itemImageContentType?: string,
    public itemImage?: any,
    public isActive?: boolean,
    public timeCreated?: Moment,
    public timeModified?: Moment,
    public userIdCreated?: number,
    public userIdModified?: number,
    public invoiceLines?: IInvoiceLine[],
    public unit?: IUnit,
    public itemGroup?: IItemGroup,
    public store?: IStore
  ) {
    this.isAllowModified = this.isAllowModified || false;
    this.isActive = this.isActive || false;
  }
}
