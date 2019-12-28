package com.ak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ak.domain.enumeration.ProcessStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ak.domain.InvoiceLine} entity. This class is used
 * in {@link com.ak.web.rest.InvoiceLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceLineCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProcessStatus
     */
    public static class ProcessStatusFilter extends Filter<ProcessStatus> {

        public ProcessStatusFilter() {
        }

        public ProcessStatusFilter(ProcessStatusFilter filter) {
            super(filter);
        }

        @Override
        public ProcessStatusFilter copy() {
            return new ProcessStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter displayOrder;

    private StringFilter itemName;

    private StringFilter unitName;

    private DoubleFilter quantity;

    private DoubleFilter rate;

    private DoubleFilter amount;

    private DoubleFilter discountPct;

    private StringFilter accountNumber;

    private ProcessStatusFilter status;

    private LongFilter invoiceId;

    private LongFilter itemId;

    private LongFilter companyId;

    public InvoiceLineCriteria(){
    }

    public InvoiceLineCriteria(InvoiceLineCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.displayOrder = other.displayOrder == null ? null : other.displayOrder.copy();
        this.itemName = other.itemName == null ? null : other.itemName.copy();
        this.unitName = other.unitName == null ? null : other.unitName.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.discountPct = other.discountPct == null ? null : other.discountPct.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.itemId = other.itemId == null ? null : other.itemId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public InvoiceLineCriteria copy() {
        return new InvoiceLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(IntegerFilter displayOrder) {
        this.displayOrder = displayOrder;
    }

    public StringFilter getItemName() {
        return itemName;
    }

    public void setItemName(StringFilter itemName) {
        this.itemName = itemName;
    }

    public StringFilter getUnitName() {
        return unitName;
    }

    public void setUnitName(StringFilter unitName) {
        this.unitName = unitName;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public DoubleFilter getRate() {
        return rate;
    }

    public void setRate(DoubleFilter rate) {
        this.rate = rate;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public DoubleFilter getDiscountPct() {
        return discountPct;
    }

    public void setDiscountPct(DoubleFilter discountPct) {
        this.discountPct = discountPct;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public ProcessStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ProcessStatusFilter status) {
        this.status = status;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
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
        final InvoiceLineCriteria that = (InvoiceLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(displayOrder, that.displayOrder) &&
            Objects.equals(itemName, that.itemName) &&
            Objects.equals(unitName, that.unitName) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(discountPct, that.discountPct) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(status, that.status) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        displayOrder,
        itemName,
        unitName,
        quantity,
        rate,
        amount,
        discountPct,
        accountNumber,
        status,
        invoiceId,
        itemId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "InvoiceLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (displayOrder != null ? "displayOrder=" + displayOrder + ", " : "") +
                (itemName != null ? "itemName=" + itemName + ", " : "") +
                (unitName != null ? "unitName=" + unitName + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (discountPct != null ? "discountPct=" + discountPct + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
