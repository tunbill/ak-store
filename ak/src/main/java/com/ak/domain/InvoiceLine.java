package com.ak.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.ak.domain.enumeration.ProcessStatus;

/**
 * A InvoiceLine.
 */
@Entity
@Table(name = "invoice_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvoiceLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Size(max = 150)
    @Column(name = "item_name", length = 150)
    private String itemName;

    @Size(max = 10)
    @Column(name = "unit_name", length = 10)
    private String unitName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "discount_pct")
    private Double discountPct;

    @Column(name = "account_number")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessStatus status;

    @ManyToOne
    @JsonIgnoreProperties("invoiceLines")
    private Invoice invoice;

    @ManyToOne
    @JsonIgnoreProperties("invoiceLines")
    private Item item;

    @ManyToOne
    @JsonIgnoreProperties("invoiceLines")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public InvoiceLine displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getItemName() {
        return itemName;
    }

    public InvoiceLine itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitName() {
        return unitName;
    }

    public InvoiceLine unitName(String unitName) {
        this.unitName = unitName;
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public InvoiceLine quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRate() {
        return rate;
    }

    public InvoiceLine rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public InvoiceLine amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscountPct() {
        return discountPct;
    }

    public InvoiceLine discountPct(Double discountPct) {
        this.discountPct = discountPct;
        return this;
    }

    public void setDiscountPct(Double discountPct) {
        this.discountPct = discountPct;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public InvoiceLine accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public InvoiceLine status(ProcessStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public InvoiceLine invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Item getItem() {
        return item;
    }

    public InvoiceLine item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Company getCompany() {
        return company;
    }

    public InvoiceLine company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceLine)) {
            return false;
        }
        return id != null && id.equals(((InvoiceLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" +
            "id=" + getId() +
            ", displayOrder=" + getDisplayOrder() +
            ", itemName='" + getItemName() + "'" +
            ", unitName='" + getUnitName() + "'" +
            ", quantity=" + getQuantity() +
            ", rate=" + getRate() +
            ", amount=" + getAmount() +
            ", discountPct=" + getDiscountPct() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
