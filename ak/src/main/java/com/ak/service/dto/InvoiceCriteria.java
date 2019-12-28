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
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.ak.domain.Invoice} entity. This class is used
 * in {@link com.ak.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceCriteria implements Serializable, Criteria {
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

    private StringFilter invoiceNo;

    private LocalDateFilter invoiceDate;

    private LocalDateFilter dueDate;

    private StringFilter billingAddress;

    private StringFilter accountNumber;

    private StringFilter poNumber;

    private StringFilter notes;

    private DoubleFilter productTotal;

    private DoubleFilter vatTotal;

    private DoubleFilter discountTotal;

    private DoubleFilter total;

    private ProcessStatusFilter status;

    private InstantFilter timeCreated;

    private InstantFilter timeModified;

    private LongFilter userIdCreated;

    private LongFilter userIdModified;

    private LongFilter invoiceLineId;

    private LongFilter customerId;

    private LongFilter termsId;

    private LongFilter employeeId;

    private LongFilter companyId;

    public InvoiceCriteria(){
    }

    public InvoiceCriteria(InvoiceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceNo = other.invoiceNo == null ? null : other.invoiceNo.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.billingAddress = other.billingAddress == null ? null : other.billingAddress.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.poNumber = other.poNumber == null ? null : other.poNumber.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.productTotal = other.productTotal == null ? null : other.productTotal.copy();
        this.vatTotal = other.vatTotal == null ? null : other.vatTotal.copy();
        this.discountTotal = other.discountTotal == null ? null : other.discountTotal.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.timeCreated = other.timeCreated == null ? null : other.timeCreated.copy();
        this.timeModified = other.timeModified == null ? null : other.timeModified.copy();
        this.userIdCreated = other.userIdCreated == null ? null : other.userIdCreated.copy();
        this.userIdModified = other.userIdModified == null ? null : other.userIdModified.copy();
        this.invoiceLineId = other.invoiceLineId == null ? null : other.invoiceLineId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.termsId = other.termsId == null ? null : other.termsId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(StringFilter invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDateFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public StringFilter getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(StringFilter billingAddress) {
        this.billingAddress = billingAddress;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(StringFilter poNumber) {
        this.poNumber = poNumber;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public DoubleFilter getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(DoubleFilter productTotal) {
        this.productTotal = productTotal;
    }

    public DoubleFilter getVatTotal() {
        return vatTotal;
    }

    public void setVatTotal(DoubleFilter vatTotal) {
        this.vatTotal = vatTotal;
    }

    public DoubleFilter getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(DoubleFilter discountTotal) {
        this.discountTotal = discountTotal;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public ProcessStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ProcessStatusFilter status) {
        this.status = status;
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

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getTermsId() {
        return termsId;
    }

    public void setTermsId(LongFilter termsId) {
        this.termsId = termsId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceNo, that.invoiceNo) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(billingAddress, that.billingAddress) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(poNumber, that.poNumber) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(productTotal, that.productTotal) &&
            Objects.equals(vatTotal, that.vatTotal) &&
            Objects.equals(discountTotal, that.discountTotal) &&
            Objects.equals(total, that.total) &&
            Objects.equals(status, that.status) &&
            Objects.equals(timeCreated, that.timeCreated) &&
            Objects.equals(timeModified, that.timeModified) &&
            Objects.equals(userIdCreated, that.userIdCreated) &&
            Objects.equals(userIdModified, that.userIdModified) &&
            Objects.equals(invoiceLineId, that.invoiceLineId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(termsId, that.termsId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoiceNo,
        invoiceDate,
        dueDate,
        billingAddress,
        accountNumber,
        poNumber,
        notes,
        productTotal,
        vatTotal,
        discountTotal,
        total,
        status,
        timeCreated,
        timeModified,
        userIdCreated,
        userIdModified,
        invoiceLineId,
        customerId,
        termsId,
        employeeId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoiceNo != null ? "invoiceNo=" + invoiceNo + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (billingAddress != null ? "billingAddress=" + billingAddress + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (poNumber != null ? "poNumber=" + poNumber + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (productTotal != null ? "productTotal=" + productTotal + ", " : "") +
                (vatTotal != null ? "vatTotal=" + vatTotal + ", " : "") +
                (discountTotal != null ? "discountTotal=" + discountTotal + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (timeCreated != null ? "timeCreated=" + timeCreated + ", " : "") +
                (timeModified != null ? "timeModified=" + timeModified + ", " : "") +
                (userIdCreated != null ? "userIdCreated=" + userIdCreated + ", " : "") +
                (userIdModified != null ? "userIdModified=" + userIdModified + ", " : "") +
                (invoiceLineId != null ? "invoiceLineId=" + invoiceLineId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (termsId != null ? "termsId=" + termsId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
