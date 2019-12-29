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

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "is_vendor")
    private Boolean isVendor;

    @Column(name = "vendor_id")
    private Long vendorId;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "address")
    private String address;

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    @Size(max = 50)
    @Column(name = "mobile", length = 50)
    private String mobile;

    @Size(max = 50)
    @Column(name = "fax", length = 50)
    private String fax;

    @Size(max = 50)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 30)
    @Column(name = "tax_code", length = 30)
    private String taxCode;

    @Size(max = 20)
    @Column(name = "account_number", length = 20)
    private String accountNumber;

    @Size(max = 20)
    @Column(name = "bank_account", length = 20)
    private String bankAccount;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @Column(name = "total_balance", precision = 21, scale = 2)
    private BigDecimal totalBalance;

    @Column(name = "open_balance", precision = 21, scale = 2)
    private BigDecimal openBalance;

    @Column(name = "open_balance_date")
    private Instant openBalanceDate;

    @Column(name = "credit_limit", precision = 21, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "notes")
    private String notes;

    @Size(max = 50)
    @Column(name = "contact_name", length = 50)
    private String contactName;

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

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("customers")
    private CustomerType customerType;

    @ManyToOne
    @JsonIgnoreProperties("customers")
    private Terms terms;

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

    public Customer companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Boolean isIsVendor() {
        return isVendor;
    }

    public Customer isVendor(Boolean isVendor) {
        this.isVendor = isVendor;
        return this;
    }

    public void setIsVendor(Boolean isVendor) {
        this.isVendor = isVendor;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public Customer vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getCode() {
        return code;
    }

    public Customer code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Customer companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public Customer mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public Customer fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public Customer taxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public Customer bankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public Customer bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Customer balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public Customer totalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
        return this;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public Customer openBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
        return this;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public Instant getOpenBalanceDate() {
        return openBalanceDate;
    }

    public Customer openBalanceDate(Instant openBalanceDate) {
        this.openBalanceDate = openBalanceDate;
        return this;
    }

    public void setOpenBalanceDate(Instant openBalanceDate) {
        this.openBalanceDate = openBalanceDate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Customer creditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getNotes() {
        return notes;
    }

    public Customer notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContactName() {
        return contactName;
    }

    public Customer contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Customer isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public Customer timeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Instant getTimeModified() {
        return timeModified;
    }

    public Customer timeModified(Instant timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(Instant timeModified) {
        this.timeModified = timeModified;
    }

    public Long getUserIdCreated() {
        return userIdCreated;
    }

    public Customer userIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
        return this;
    }

    public void setUserIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
    }

    public Long getUserIdModified() {
        return userIdModified;
    }

    public Customer userIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
        return this;
    }

    public void setUserIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Customer invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Customer addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCustomer(this);
        return this;
    }

    public Customer removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCustomer(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public Customer customerType(CustomerType customerType) {
        this.customerType = customerType;
        return this;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Terms getTerms() {
        return terms;
    }

    public Customer terms(Terms terms) {
        this.terms = terms;
        return this;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", isVendor='" + isIsVendor() + "'" +
            ", vendorId=" + getVendorId() +
            ", code='" + getCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", balance=" + getBalance() +
            ", totalBalance=" + getTotalBalance() +
            ", openBalance=" + getOpenBalance() +
            ", openBalanceDate='" + getOpenBalanceDate() + "'" +
            ", creditLimit=" + getCreditLimit() +
            ", notes='" + getNotes() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", timeCreated='" + getTimeCreated() + "'" +
            ", timeModified='" + getTimeModified() + "'" +
            ", userIdCreated=" + getUserIdCreated() +
            ", userIdModified=" + getUserIdModified() +
            "}";
    }
}
