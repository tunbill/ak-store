import { ItemType, PriceMethod, VATTax } from './enumerations';
import { IInvoiceLine } from './invoice-line.model';
import { IUnit } from './unit.model';
import { IItemGroup } from './item-group.model';
import { IStore } from './store.model';

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
  timeCreated?: Date;
  timeModified?: Date;
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
    public timeCreated?: Date,
    public timeModified?: Date,
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
