package com.ak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.ak.domain.Customer} entity. This class is used
 * in {@link com.ak.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter companyId;

    private BooleanFilter isVendor;

    private LongFilter vendorId;

    private StringFilter code;

    private StringFilter companyName;

    private StringFilter address;

    private StringFilter phone;

    private StringFilter mobile;

    private StringFilter fax;

    private StringFilter email;

    private StringFilter taxCode;

    private StringFilter accountNumber;

    private StringFilter bankAccount;

    private StringFilter bankName;

    private BigDecimalFilter balance;

    private BigDecimalFilter totalBalance;

    private BigDecimalFilter openBalance;

    private InstantFilter openBalanceDate;

    private BigDecimalFilter creditLimit;

    private StringFilter notes;

    private StringFilter contactName;

    private BooleanFilter isActive;

    private InstantFilter timeCreated;

    private InstantFilter timeModified;

    private LongFilter userIdCreated;

    private LongFilter userIdModified;

    private LongFilter invoiceId;

    private LongFilter customerTypeId;

    private LongFilter termsId;

    public CustomerCriteria(){
    }

    public CustomerCriteria(CustomerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.isVendor = other.isVendor == null ? null : other.isVendor.copy();
        this.vendorId = other.vendorId == null ? null : other.vendorId.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.taxCode = other.taxCode == null ? null : other.taxCode.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.bankAccount = other.bankAccount == null ? null : other.bankAccount.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.balance = other.balance == null ? null : other.balance.copy();
        this.totalBalance = other.totalBalance == null ? null : other.totalBalance.copy();
        this.openBalance = other.openBalance == null ? null : other.openBalance.copy();
        this.openBalanceDate = other.openBalanceDate == null ? null : other.openBalanceDate.copy();
        this.creditLimit = other.creditLimit == null ? null : other.creditLimit.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.timeCreated = other.timeCreated == null ? null : other.timeCreated.copy();
        this.timeModified = other.timeModified == null ? null : other.timeModified.copy();
        this.userIdCreated = other.userIdCreated == null ? null : other.userIdCreated.copy();
        this.userIdModified = other.userIdModified == null ? null : other.userIdModified.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.customerTypeId = other.customerTypeId == null ? null : other.customerTypeId.copy();
        this.termsId = other.termsId == null ? null : other.termsId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public BooleanFilter getIsVendor() {
        return isVendor;
    }

    public void setIsVendor(BooleanFilter isVendor) {
        this.isVendor = isVendor;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(StringFilter taxCode) {
        this.taxCode = taxCode;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(StringFilter bankAccount) {
        this.bankAccount = bankAccount;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public BigDecimalFilter getBalance() {
        return balance;
    }

    public void setBalance(BigDecimalFilter balance) {
        this.balance = balance;
    }

    public BigDecimalFilter getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimalFilter totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimalFilter getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimalFilter openBalance) {
        this.openBalance = openBalance;
    }

    public InstantFilter getOpenBalanceDate() {
        return openBalanceDate;
    }

    public void setOpenBalanceDate(InstantFilter openBalanceDate) {
        this.openBalanceDate = openBalanceDate;
    }

    public BigDecimalFilter getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimalFilter creditLimit) {
        this.creditLimit = creditLimit;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
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

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(LongFilter customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public LongFilter getTermsId() {
        return termsId;
    }

    public void setTermsId(LongFilter termsId) {
        this.termsId = termsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(isVendor, that.isVendor) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(email, that.email) &&
            Objects.equals(taxCode, that.taxCode) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(bankAccount, that.bankAccount) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(totalBalance, that.totalBalance) &&
            Objects.equals(openBalance, that.openBalance) &&
            Objects.equals(openBalanceDate, that.openBalanceDate) &&
            Objects.equals(creditLimit, that.creditLimit) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(timeCreated, that.timeCreated) &&
            Objects.equals(timeModified, that.timeModified) &&
            Objects.equals(userIdCreated, that.userIdCreated) &&
            Objects.equals(userIdModified, that.userIdModified) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(customerTypeId, that.customerTypeId) &&
            Objects.equals(termsId, that.termsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyId,
        isVendor,
        vendorId,
        code,
        companyName,
        address,
        phone,
        mobile,
        fax,
        email,
        taxCode,
        accountNumber,
        bankAccount,
        bankName,
        balance,
        totalBalance,
        openBalance,
        openBalanceDate,
        creditLimit,
        notes,
        contactName,
        isActive,
        timeCreated,
        timeModified,
        userIdCreated,
        userIdModified,
        invoiceId,
        customerTypeId,
        termsId
        );
    }

    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (isVendor != null ? "isVendor=" + isVendor + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (taxCode != null ? "taxCode=" + taxCode + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (bankAccount != null ? "bankAccount=" + bankAccount + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (totalBalance != null ? "totalBalance=" + totalBalance + ", " : "") +
                (openBalance != null ? "openBalance=" + openBalance + ", " : "") +
                (openBalanceDate != null ? "openBalanceDate=" + openBalanceDate + ", " : "") +
                (creditLimit != null ? "creditLimit=" + creditLimit + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (contactName != null ? "contactName=" + contactName + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (timeCreated != null ? "timeCreated=" + timeCreated + ", " : "") +
                (timeModified != null ? "timeModified=" + timeModified + ", " : "") +
                (userIdCreated != null ? "userIdCreated=" + userIdCreated + ", " : "") +
                (userIdModified != null ? "userIdModified=" + userIdModified + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (customerTypeId != null ? "customerTypeId=" + customerTypeId + ", " : "") +
                (termsId != null ? "termsId=" + termsId + ", " : "") +
            "}";
    }

}
