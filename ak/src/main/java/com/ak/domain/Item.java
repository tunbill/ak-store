package com.ak.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.ak.domain.enumeration.ItemType;

import com.ak.domain.enumeration.VATTax;

import com.ak.domain.enumeration.PriceMethod;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @NotNull
    @Size(max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ItemType type;

    @Column(name = "sku_num", precision = 21, scale = 2)
    private BigDecimal skuNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "vat_tax")
    private VATTax vatTax;

    @Column(name = "import_tax", precision = 21, scale = 2)
    private BigDecimal importTax;

    @Column(name = "export_tax", precision = 21, scale = 2)
    private BigDecimal exportTax;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_price_method")
    private PriceMethod inventoryPriceMethod;

    @Size(max = 10)
    @Column(name = "account_item", length = 10)
    private String accountItem;

    @Column(name = "is_allow_modified")
    private Boolean isAllowModified;

    @Size(max = 10)
    @Column(name = "account_cost", length = 10)
    private String accountCost;

    @Size(max = 10)
    @Column(name = "account_revenue", length = 10)
    private String accountRevenue;

    @Size(max = 10)
    @Column(name = "account_internal_revenue", length = 10)
    private String accountInternalRevenue;

    @Size(max = 10)
    @Column(name = "account_sale_return", length = 10)
    private String accountSaleReturn;

    @Size(max = 10)
    @Column(name = "account_sale_price", length = 10)
    private String accountSalePrice;

    @Size(max = 10)
    @Column(name = "account_agency", length = 10)
    private String accountAgency;

    @Size(max = 10)
    @Column(name = "account_raw_product", length = 10)
    private String accountRawProduct;

    @Size(max = 10)
    @Column(name = "account_cost_difference", length = 10)
    private String accountCostDifference;

    @Size(max = 10)
    @Column(name = "account_discount", length = 10)
    private String accountDiscount;

    @Size(max = 100)
    @Column(name = "sale_desc", length = 100)
    private String saleDesc;

    @Size(max = 100)
    @Column(name = "purchase_desc", length = 100)
    private String purchaseDesc;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "lenght")
    private Double lenght;

    @Column(name = "wide")
    private Double wide;

    @Column(name = "height")
    private Double height;

    @Column(name = "color")
    private String color;

    @Column(name = "specification")
    private String specification;

    @Lob
    @Column(name = "item_image")
    private byte[] itemImage;

    @Column(name = "item_image_content_type")
    private String itemImageContentType;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "time_created")
    private Instant timeCreated;

    @Column(name = "time_modified")
    private Instant timeModified;

    @Column(name = "user_id_created")
    private Long userIdCreated;

    @Column(name = "user_id_modified")
    private Long userIdModified;

    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Unit unit;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private ItemGroup itemGroup;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Item companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCode() {
        return code;
    }

    public Item code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Item description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getType() {
        return type;
    }

    public Item type(ItemType type) {
        this.type = type;
        return this;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public BigDecimal getSkuNum() {
        return skuNum;
    }

    public Item skuNum(BigDecimal skuNum) {
        this.skuNum = skuNum;
        return this;
    }

    public void setSkuNum(BigDecimal skuNum) {
        this.skuNum = skuNum;
    }

    public VATTax getVatTax() {
        return vatTax;
    }

    public Item vatTax(VATTax vatTax) {
        this.vatTax = vatTax;
        return this;
    }

    public void setVatTax(VATTax vatTax) {
        this.vatTax = vatTax;
    }

    public BigDecimal getImportTax() {
        return importTax;
    }

    public Item importTax(BigDecimal importTax) {
        this.importTax = importTax;
        return this;
    }

    public void setImportTax(BigDecimal importTax) {
        this.importTax = importTax;
    }

    public BigDecimal getExportTax() {
        return exportTax;
    }

    public Item exportTax(BigDecimal exportTax) {
        this.exportTax = exportTax;
        return this;
    }

    public void setExportTax(BigDecimal exportTax) {
        this.exportTax = exportTax;
    }

    public PriceMethod getInventoryPriceMethod() {
        return inventoryPriceMethod;
    }

    public Item inventoryPriceMethod(PriceMethod inventoryPriceMethod) {
        this.inventoryPriceMethod = inventoryPriceMethod;
        return this;
    }

    public void setInventoryPriceMethod(PriceMethod inventoryPriceMethod) {
        this.inventoryPriceMethod = inventoryPriceMethod;
    }

    public String getAccountItem() {
        return accountItem;
    }

    public Item accountItem(String accountItem) {
        this.accountItem = accountItem;
        return this;
    }

    public void setAccountItem(String accountItem) {
        this.accountItem = accountItem;
    }

    public Boolean isIsAllowModified() {
        return isAllowModified;
    }

    public Item isAllowModified(Boolean isAllowModified) {
        this.isAllowModified = isAllowModified;
        return this;
    }

    public void setIsAllowModified(Boolean isAllowModified) {
        this.isAllowModified = isAllowModified;
    }

    public String getAccountCost() {
        return accountCost;
    }

    public Item accountCost(String accountCost) {
        this.accountCost = accountCost;
        return this;
    }

    public void setAccountCost(String accountCost) {
        this.accountCost = accountCost;
    }

    public String getAccountRevenue() {
        return accountRevenue;
    }

    public Item accountRevenue(String accountRevenue) {
        this.accountRevenue = accountRevenue;
        return this;
    }

    public void setAccountRevenue(String accountRevenue) {
        this.accountRevenue = accountRevenue;
    }

    public String getAccountInternalRevenue() {
        return accountInternalRevenue;
    }

    public Item accountInternalRevenue(String accountInternalRevenue) {
        this.accountInternalRevenue = accountInternalRevenue;
        return this;
    }

    public void setAccountInternalRevenue(String accountInternalRevenue) {
        this.accountInternalRevenue = accountInternalRevenue;
    }

    public String getAccountSaleReturn() {
        return accountSaleReturn;
    }

    public Item accountSaleReturn(String accountSaleReturn) {
        this.accountSaleReturn = accountSaleReturn;
        return this;
    }

    public void setAccountSaleReturn(String accountSaleReturn) {
        this.accountSaleReturn = accountSaleReturn;
    }

    public String getAccountSalePrice() {
        return accountSalePrice;
    }

    public Item accountSalePrice(String accountSalePrice) {
        this.accountSalePrice = accountSalePrice;
        return this;
    }

    public void setAccountSalePrice(String accountSalePrice) {
        this.accountSalePrice = accountSalePrice;
    }

    public String getAccountAgency() {
        return accountAgency;
    }

    public Item accountAgency(String accountAgency) {
        this.accountAgency = accountAgency;
        return this;
    }

    public void setAccountAgency(String accountAgency) {
        this.accountAgency = accountAgency;
    }

    public String getAccountRawProduct() {
        return accountRawProduct;
    }

    public Item accountRawProduct(String accountRawProduct) {
        this.accountRawProduct = accountRawProduct;
        return this;
    }

    public void setAccountRawProduct(String accountRawProduct) {
        this.accountRawProduct = accountRawProduct;
    }

    public String getAccountCostDifference() {
        return accountCostDifference;
    }

    public Item accountCostDifference(String accountCostDifference) {
        this.accountCostDifference = accountCostDifference;
        return this;
    }

    public void setAccountCostDifference(String accountCostDifference) {
        this.accountCostDifference = accountCostDifference;
    }

    public String getAccountDiscount() {
        return accountDiscount;
    }

    public Item accountDiscount(String accountDiscount) {
        this.accountDiscount = accountDiscount;
        return this;
    }

    public void setAccountDiscount(String accountDiscount) {
        this.accountDiscount = accountDiscount;
    }

    public String getSaleDesc() {
        return saleDesc;
    }

    public Item saleDesc(String saleDesc) {
        this.saleDesc = saleDesc;
        return this;
    }

    public void setSaleDesc(String saleDesc) {
        this.saleDesc = saleDesc;
    }

    public String getPurchaseDesc() {
        return purchaseDesc;
    }

    public Item purchaseDesc(String purchaseDesc) {
        this.purchaseDesc = purchaseDesc;
        return this;
    }

    public void setPurchaseDesc(String purchaseDesc) {
        this.purchaseDesc = purchaseDesc;
    }

    public Double getWeight() {
        return weight;
    }

    public Item weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLenght() {
        return lenght;
    }

    public Item lenght(Double lenght) {
        this.lenght = lenght;
        return this;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    public Double getWide() {
        return wide;
    }

    public Item wide(Double wide) {
        this.wide = wide;
        return this;
    }

    public void setWide(Double wide) {
        this.wide = wide;
    }

    public Double getHeight() {
        return height;
    }

    public Item height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public Item color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecification() {
        return specification;
    }

    public Item specification(String specification) {
        this.specification = specification;
        return this;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public Item itemImage(byte[] itemImage) {
        this.itemImage = itemImage;
        return this;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemImageContentType() {
        return itemImageContentType;
    }

    public Item itemImageContentType(String itemImageContentType) {
        this.itemImageContentType = itemImageContentType;
        return this;
    }

    public void setItemImageContentType(String itemImageContentType) {
        this.itemImageContentType = itemImageContentType;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Item isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public Item timeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Instant getTimeModified() {
        return timeModified;
    }

    public Item timeModified(Instant timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(Instant timeModified) {
        this.timeModified = timeModified;
    }

    public Long getUserIdCreated() {
        return userIdCreated;
    }

    public Item userIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
        return this;
    }

    public void setUserIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
    }

    public Long getUserIdModified() {
        return userIdModified;
    }

    public Item userIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
        return this;
    }

    public void setUserIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
    }

    public Set<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public Item invoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
        return this;
    }

    public Item addInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLines.add(invoiceLine);
        invoiceLine.setItem(this);
        return this;
    }

    public Item removeInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLines.remove(invoiceLine);
        invoiceLine.setItem(null);
        return this;
    }

    public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public Unit getUnit() {
        return unit;
    }

    public Item unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public Item itemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Store getStore() {
        return store;
    }

    public Item store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", skuNum=" + getSkuNum() +
            ", vatTax='" + getVatTax() + "'" +
            ", importTax=" + getImportTax() +
            ", exportTax=" + getExportTax() +
            ", inventoryPriceMethod='" + getInventoryPriceMethod() + "'" +
            ", accountItem='" + getAccountItem() + "'" +
            ", isAllowModified='" + isIsAllowModified() + "'" +
            ", accountCost='" + getAccountCost() + "'" +
            ", accountRevenue='" + getAccountRevenue() + "'" +
            ", accountInternalRevenue='" + getAccountInternalRevenue() + "'" +
            ", accountSaleReturn='" + getAccountSaleReturn() + "'" +
            ", accountSalePrice='" + getAccountSalePrice() + "'" +
            ", accountAgency='" + getAccountAgency() + "'" +
            ", accountRawProduct='" + getAccountRawProduct() + "'" +
            ", accountCostDifference='" + getAccountCostDifference() + "'" +
            ", accountDiscount='" + getAccountDiscount() + "'" +
            ", saleDesc='" + getSaleDesc() + "'" +
            ", purchaseDesc='" + getPurchaseDesc() + "'" +
            ", weight=" + getWeight() +
            ", lenght=" + getLenght() +
            ", wide=" + getWide() +
            ", height=" + getHeight() +
            ", color='" + getColor() + "'" +
            ", specification='" + getSpecification() + "'" +
            ", itemImage='" + getItemImage() + "'" +
            ", itemImageContentType='" + getItemImageContentType() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", timeCreated='" + getTimeCreated() + "'" +
            ", timeModified='" + getTimeModified() + "'" +
            ", userIdCreated=" + getUserIdCreated() +
            ", userIdModified=" + getUserIdModified() +
            "}";
    }
}
