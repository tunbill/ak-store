package com.ak.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.ak.domain.enumeration.ProcessStatus;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Size(max = 20)
    @Column(name = "invoice_no", length = 20)
    private String invoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Size(max = 300)
    @Column(name = "billing_address", length = 300)
    private String billingAddress;

    @Size(max = 10)
    @Column(name = "account_number", length = 10)
    private String accountNumber;

    @Size(max = 20)
    @Column(name = "po_number", length = 20)
    private String poNumber;

    @Size(max = 200)
    @Column(name = "notes", length = 200)
    private String notes;

    @Column(name = "product_total", precision = 21, scale = 2)
    private BigDecimal productTotal;

    @Column(name = "vat_total", precision = 21, scale = 2)
    private BigDecimal vatTotal;

    @Column(name = "discount_total", precision = 21, scale = 2)
    private BigDecimal discountTotal;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessStatus status;

    @Column(name = "time_created")
    private Instant timeCreated;

    @Column(name = "time_modified")
    private Instant timeModified;

    @Column(name = "user_id_created")
    private Long userIdCreated;

    @Column(name = "user_id_modified")
    private Long userIdModified;

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Terms terms;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Employee employee;

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

    public Invoice companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public Invoice invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Invoice dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public Invoice billingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Invoice accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public Invoice poNumber(String poNumber) {
        this.poNumber = poNumber;
        return this;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getNotes() {
        return notes;
    }

    public Invoice notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getProductTotal() {
        return productTotal;
    }

    public Invoice productTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
        return this;
    }

    public void setProductTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
    }

    public BigDecimal getVatTotal() {
        return vatTotal;
    }

    public Invoice vatTotal(BigDecimal vatTotal) {
        this.vatTotal = vatTotal;
        return this;
    }

    public void setVatTotal(BigDecimal vatTotal) {
        this.vatTotal = vatTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public Invoice discountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
        return this;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Invoice total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public Invoice status(ProcessStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public Invoice timeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Instant getTimeModified() {
        return timeModified;
    }

    public Invoice timeModified(Instant timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(Instant timeModified) {
        this.timeModified = timeModified;
    }

    public Long getUserIdCreated() {
        return userIdCreated;
    }

    public Invoice userIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
        return this;
    }

    public void setUserIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
    }

    public Long getUserIdModified() {
        return userIdModified;
    }

    public Invoice userIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
        return this;
    }

    public void setUserIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
    }

    public Set<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public Invoice invoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
        return this;
    }

    public Invoice addInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLines.add(invoiceLine);
        invoiceLine.setInvoice(this);
        return this;
    }

    public Invoice removeInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLines.remove(invoiceLine);
        invoiceLine.setInvoice(null);
        return this;
    }

    public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Invoice customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Terms getTerms() {
        return terms;
    }

    public Invoice terms(Terms terms) {
        this.terms = terms;
        return this;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Invoice employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", billingAddress='" + getBillingAddress() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", poNumber='" + getPoNumber() + "'" +
            ", notes='" + getNotes() + "'" +
            ", productTotal=" + getProductTotal() +
            ", vatTotal=" + getVatTotal() +
            ", discountTotal=" + getDiscountTotal() +
            ", total=" + getTotal() +
            ", status='" + getStatus() + "'" +
            ", timeCreated='" + getTimeCreated() + "'" +
            ", timeModified='" + getTimeModified() + "'" +
            ", userIdCreated=" + getUserIdCreated() +
            ", userIdModified=" + getUserIdModified() +
            "}";
    }
}
