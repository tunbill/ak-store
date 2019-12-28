package com.ak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ak.domain.enumeration.ItemType;
import com.ak.domain.enumeration.VATTax;
import com.ak.domain.enumeration.PriceMethod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.ak.domain.Item} entity. This class is used
 * in {@link com.ak.web.rest.ItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ItemType
     */
    public static class ItemTypeFilter extends Filter<ItemType> {

        public ItemTypeFilter() {
        }

        public ItemTypeFilter(ItemTypeFilter filter) {
            super(filter);
        }

        @Override
        public ItemTypeFilter copy() {
            return new ItemTypeFilter(this);
        }

    }
    /**
     * Class for filtering VATTax
     */
    public static class VATTaxFilter extends Filter<VATTax> {

        public VATTaxFilter() {
        }

        public VATTaxFilter(VATTaxFilter filter) {
            super(filter);
        }

        @Override
        public VATTaxFilter copy() {
            return new VATTaxFilter(this);
        }

    }
    /**
     * Class for filtering PriceMethod
     */
    public static class PriceMethodFilter extends Filter<PriceMethod> {

        public PriceMethodFilter() {
        }

        public PriceMethodFilter(PriceMethodFilter filter) {
            super(filter);
        }

        @Override
        public PriceMethodFilter copy() {
            return new PriceMethodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private ItemTypeFilter type;

    private DoubleFilter skuNum;

    private VATTaxFilter vatTax;

    private DoubleFilter importTax;

    private DoubleFilter exportTax;

    private PriceMethodFilter inventoryPriceMethod;

    private StringFilter accountItem;

    private BooleanFilter isAllowModified;

    private StringFilter accountCost;

    private StringFilter accountRevenue;

    private StringFilter accountInternalRevenue;

    private StringFilter accountSaleReturn;

    private StringFilter accountSalePrice;

    private StringFilter accountAgency;

    private StringFilter accountRawProduct;

    private StringFilter accountCostDifference;

    private StringFilter accountDiscount;

    private StringFilter saleDesc;

    private StringFilter purchaseDesc;

    private DoubleFilter weight;

    private DoubleFilter lenght;

    private DoubleFilter wide;

    private DoubleFilter height;

    private StringFilter color;

    private StringFilter specification;

    private BooleanFilter isActive;

    private InstantFilter timeCreated;

    private InstantFilter timeModified;

    private LongFilter userIdCreated;

    private LongFilter userIdModified;

    private LongFilter invoiceLineId;

    private LongFilter unitId;

    private LongFilter itemGroupId;

    private LongFilter storeId;

    private LongFilter companyId;

    public ItemCriteria(){
    }

    public ItemCriteria(ItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.skuNum = other.skuNum == null ? null : other.skuNum.copy();
        this.vatTax = other.vatTax == null ? null : other.vatTax.copy();
        this.importTax = other.importTax == null ? null : other.importTax.copy();
        this.exportTax = other.exportTax == null ? null : other.exportTax.copy();
        this.inventoryPriceMethod = other.inventoryPriceMethod == null ? null : other.inventoryPriceMethod.copy();
        this.accountItem = other.accountItem == null ? null : other.accountItem.copy();
        this.isAllowModified = other.isAllowModified == null ? null : other.isAllowModified.copy();
        this.accountCost = other.accountCost == null ? null : other.accountCost.copy();
        this.accountRevenue = other.accountRevenue == null ? null : other.accountRevenue.copy();
        this.accountInternalRevenue = other.accountInternalRevenue == null ? null : other.accountInternalRevenue.copy();
        this.accountSaleReturn = other.accountSaleReturn == null ? null : other.accountSaleReturn.copy();
        this.accountSalePrice = other.accountSalePrice == null ? null : other.accountSalePrice.copy();
        this.accountAgency = other.accountAgency == null ? null : other.accountAgency.copy();
        this.accountRawProduct = other.accountRawProduct == null ? null : other.accountRawProduct.copy();
        this.accountCostDifference = other.accountCostDifference == null ? null : other.accountCostDifference.copy();
        this.accountDiscount = other.accountDiscount == null ? null : other.accountDiscount.copy();
        this.saleDesc = other.saleDesc == null ? null : other.saleDesc.copy();
        this.purchaseDesc = other.purchaseDesc == null ? null : other.purchaseDesc.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.lenght = other.lenght == null ? null : other.lenght.copy();
        this.wide = other.wide == null ? null : other.wide.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.specification = other.specification == null ? null : other.specification.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.timeCreated = other.timeCreated == null ? null : other.timeCreated.copy();
        this.timeModified = other.timeModified == null ? null : other.timeModified.copy();
        this.userIdCreated = other.userIdCreated == null ? null : other.userIdCreated.copy();
        this.userIdModified = other.userIdModified == null ? null : other.userIdModified.copy();
        this.invoiceLineId = other.invoiceLineId == null ? null : other.invoiceLineId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.itemGroupId = other.itemGroupId == null ? null : other.itemGroupId.copy();
        this.storeId = other.storeId == null ? null : other.storeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public ItemCriteria copy() {
        return new ItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ItemTypeFilter getType() {
        return type;
    }

    public void setType(ItemTypeFilter type) {
        this.type = type;
    }

    public DoubleFilter getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(DoubleFilter skuNum) {
        this.skuNum = skuNum;
    }

    public VATTaxFilter getVatTax() {
        return vatTax;
    }

    public void setVatTax(VATTaxFilter vatTax) {
        this.vatTax = vatTax;
    }

    public DoubleFilter getImportTax() {
        return importTax;
    }

    public void setImportTax(DoubleFilter importTax) {
        this.importTax = importTax;
    }

    public DoubleFilter getExportTax() {
        return exportTax;
    }

    public void setExportTax(DoubleFilter exportTax) {
        this.exportTax = exportTax;
    }

    public PriceMethodFilter getInventoryPriceMethod() {
        return inventoryPriceMethod;
    }

    public void setInventoryPriceMethod(PriceMethodFilter inventoryPriceMethod) {
        this.inventoryPriceMethod = inventoryPriceMethod;
    }

    public StringFilter getAccountItem() {
        return accountItem;
    }

    public void setAccountItem(StringFilter accountItem) {
        this.accountItem = accountItem;
    }

    public BooleanFilter getIsAllowModified() {
        return isAllowModified;
    }

    public void setIsAllowModified(BooleanFilter isAllowModified) {
        this.isAllowModified = isAllowModified;
    }

    public StringFilter getAccountCost() {
        return accountCost;
    }

    public void setAccountCost(StringFilter accountCost) {
        this.accountCost = accountCost;
    }

    public StringFilter getAccountRevenue() {
        return accountRevenue;
    }

    public void setAccountRevenue(StringFilter accountRevenue) {
        this.accountRevenue = accountRevenue;
    }

    public StringFilter getAccountInternalRevenue() {
        return accountInternalRevenue;
    }

    public void setAccountInternalRevenue(StringFilter accountInternalRevenue) {
        this.accountInternalRevenue = accountInternalRevenue;
    }

    public StringFilter getAccountSaleReturn() {
        return accountSaleReturn;
    }

    public void setAccountSaleReturn(StringFilter accountSaleReturn) {
        this.accountSaleReturn = accountSaleReturn;
    }

    public StringFilter getAccountSalePrice() {
        return accountSalePrice;
    }

    public void setAccountSalePrice(StringFilter accountSalePrice) {
        this.accountSalePrice = accountSalePrice;
    }

    public StringFilter getAccountAgency() {
        return accountAgency;
    }

    public void setAccountAgency(StringFilter accountAgency) {
        this.accountAgency = accountAgency;
    }

    public StringFilter getAccountRawProduct() {
        return accountRawProduct;
    }

    public void setAccountRawProduct(StringFilter accountRawProduct) {
        this.accountRawProduct = accountRawProduct;
    }

    public StringFilter getAccountCostDifference() {
        return accountCostDifference;
    }

    public void setAccountCostDifference(StringFilter accountCostDifference) {
        this.accountCostDifference = accountCostDifference;
    }

    public StringFilter getAccountDiscount() {
        return accountDiscount;
    }

    public void setAccountDiscount(StringFilter accountDiscount) {
        this.accountDiscount = accountDiscount;
    }

    public StringFilter getSaleDesc() {
        return saleDesc;
    }

    public void setSaleDesc(StringFilter saleDesc) {
        this.saleDesc = saleDesc;
    }

    public StringFilter getPurchaseDesc() {
        return purchaseDesc;
    }

    public void setPurchaseDesc(StringFilter purchaseDesc) {
        this.purchaseDesc = purchaseDesc;
    }

    public DoubleFilter getWeight() {
        return weight;
    }

    public void setWeight(DoubleFilter weight) {
        this.weight = weight;
    }

    public DoubleFilter getLenght() {
        return lenght;
    }

    public void setLenght(DoubleFilter lenght) {
        this.lenght = lenght;
    }

    public DoubleFilter getWide() {
        return wide;
    }

    public void setWide(DoubleFilter wide) {
        this.wide = wide;
    }

    public DoubleFilter getHeight() {
        return height;
    }

    public void setHeight(DoubleFilter height) {
        this.height = height;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public StringFilter getSpecification() {
        return specification;
    }

    public void setSpecification(StringFilter specification) {
        this.specification = specification;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public InstantFilter getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(InstantFilter timeCreated) {
        this.timeCreated = timeCreated;
    }

    public InstantFilter getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(InstantFilter timeModified) {
        this.timeModified = timeModified;
    }

    public LongFilter getUserIdCreated() {
        return userIdCreated;
    }

    public void setUserIdCreated(LongFilter userIdCreated) {
        this.userIdCreated = userIdCreated;
    }

    public LongFilter getUserIdModified() {
        return userIdModified;
    }

    public void setUserIdModified(LongFilter userIdModified) {
        this.userIdModified = userIdModified;
    }

    public LongFilter getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(LongFilter invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }

    public LongFilter getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(LongFilter itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public LongFilter getStoreId() {
        return storeId;
    }

    public void setStoreId(LongFilter storeId) {
        this.storeId = storeId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemCriteria that = (ItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(skuNum, that.skuNum) &&
            Objects.equals(vatTax, that.vatTax) &&
            Objects.equals(importTax, that.importTax) &&
            Objects.equals(exportTax, that.exportTax) &&
            Objects.equals(inventoryPriceMethod, that.inventoryPriceMethod) &&
            Objects.equals(accountItem, that.accountItem) &&
            Objects.equals(isAllowModified, that.isAllowModified) &&
            Objects.equals(accountCost, that.accountCost) &&
            Objects.equals(accountRevenue, that.accountRevenue) &&
            Objects.equals(accountInternalRevenue, that.accountInternalRevenue) &&
            Objects.equals(accountSaleReturn, that.accountSaleReturn) &&
            Objects.equals(accountSalePrice, that.accountSalePrice) &&
            Objects.equals(accountAgency, that.accountAgency) &&
            Objects.equals(accountRawProduct, that.accountRawProduct) &&
            Objects.equals(accountCostDifference, that.accountCostDifference) &&
            Objects.equals(accountDiscount, that.accountDiscount) &&
            Objects.equals(saleDesc, that.saleDesc) &&
            Objects.equals(purchaseDesc, that.purchaseDesc) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(lenght, that.lenght) &&
            Objects.equals(wide, that.wide) &&
            Objects.equals(height, that.height) &&
            Objects.equals(color, that.color) &&
            Objects.equals(specification, that.specification) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(timeCreated, that.timeCreated) &&
            Objects.equals(timeModified, that.timeModified) &&
            Objects.equals(userIdCreated, that.userIdCreated) &&
            Objects.equals(userIdModified, that.userIdModified) &&
            Objects.equals(invoiceLineId, that.invoiceLineId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(itemGroupId, that.itemGroupId) &&
            Objects.equals(storeId, that.storeId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        description,
        type,
        skuNum,
        vatTax,
        importTax,
        exportTax,
        inventoryPriceMethod,
        accountItem,
        isAllowModified,
        accountCost,
        accountRevenue,
        accountInternalRevenue,
        accountSaleReturn,
        accountSalePrice,
        accountAgency,
        accountRawProduct,
        accountCostDifference,
        accountDiscount,
        saleDesc,
        purchaseDesc,
        weight,
        lenght,
        wide,
        height,
        color,
        specification,
        isActive,
        timeCreated,
        timeModified,
        userIdCreated,
        userIdModified,
        invoiceLineId,
        unitId,
        itemGroupId,
        storeId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "ItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (skuNum != null ? "skuNum=" + skuNum + ", " : "") +
                (vatTax != null ? "vatTax=" + vatTax + ", " : "") +
                (importTax != null ? "importTax=" + importTax + ", " : "") +
                (exportTax != null ? "exportTax=" + exportTax + ", " : "") +
                (inventoryPriceMethod != null ? "inventoryPriceMethod=" + inventoryPriceMethod + ", " : "") +
                (accountItem != null ? "accountItem=" + accountItem + ", " : "") +
                (isAllowModified != null ? "isAllowModified=" + isAllowModified + ", " : "") +
                (accountCost != null ? "accountCost=" + accountCost + ", " : "") +
                (accountRevenue != null ? "accountRevenue=" + accountRevenue + ", " : "") +
                (accountInternalRevenue != null ? "accountInternalRevenue=" + accountInternalRevenue + ", " : "") +
                (accountSaleReturn != null ? "accountSaleReturn=" + accountSaleReturn + ", " : "") +
                (accountSalePrice != null ? "accountSalePrice=" + accountSalePrice + ", " : "") +
                (accountAgency != null ? "accountAgency=" + accountAgency + ", " : "") +
                (accountRawProduct != null ? "accountRawProduct=" + accountRawProduct + ", " : "") +
                (accountCostDifference != null ? "accountCostDifference=" + accountCostDifference + ", " : "") +
                (accountDiscount != null ? "accountDiscount=" + accountDiscount + ", " : "") +
                (saleDesc != null ? "saleDesc=" + saleDesc + ", " : "") +
                (purchaseDesc != null ? "purchaseDesc=" + purchaseDesc + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (lenght != null ? "lenght=" + lenght + ", " : "") +
                (wide != null ? "wide=" + wide + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (specification != null ? "specification=" + specification + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (timeCreated != null ? "timeCreated=" + timeCreated + ", " : "") +
                (timeModified != null ? "timeModified=" + timeModified + ", " : "") +
                (userIdCreated != null ? "userIdCreated=" + userIdCreated + ", " : "") +
                (userIdModified != null ? "userIdModified=" + userIdModified + ", " : "") +
                (invoiceLineId != null ? "invoiceLineId=" + invoiceLineId + ", " : "") +
                (unitId != null ? "unitId=" + unitId + ", " : "") +
                (itemGroupId != null ? "itemGroupId=" + itemGroupId + ", " : "") +
                (storeId != null ? "storeId=" + storeId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
