package com.ak.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Terms.
 */
@Entity
@Table(name = "terms")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Terms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "code", length = 10)
    private String code;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "day_of_month_due")
    private Integer dayOfMonthDue;

    @Column(name = "due_next_month_days")
    private Integer dueNextMonthDays;

    @Column(name = "discount_day_of_month")
    private Integer discountDayOfMonth;

    @Column(name = "discount_pct")
    private Double discountPct;

    @OneToMany(mappedBy = "terms")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "terms")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Terms code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Terms name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDayOfMonthDue() {
        return dayOfMonthDue;
    }

    public Terms dayOfMonthDue(Integer dayOfMonthDue) {
        this.dayOfMonthDue = dayOfMonthDue;
        return this;
    }

    public void setDayOfMonthDue(Integer dayOfMonthDue) {
        this.dayOfMonthDue = dayOfMonthDue;
    }

    public Integer getDueNextMonthDays() {
        return dueNextMonthDays;
    }

    public Terms dueNextMonthDays(Integer dueNextMonthDays) {
        this.dueNextMonthDays = dueNextMonthDays;
        return this;
    }

    public void setDueNextMonthDays(Integer dueNextMonthDays) {
        this.dueNextMonthDays = dueNextMonthDays;
    }

    public Integer getDiscountDayOfMonth() {
        return discountDayOfMonth;
    }

    public Terms discountDayOfMonth(Integer discountDayOfMonth) {
        this.discountDayOfMonth = discountDayOfMonth;
        return this;
    }

    public void setDiscountDayOfMonth(Integer discountDayOfMonth) {
        this.discountDayOfMonth = discountDayOfMonth;
    }

    public Double getDiscountPct() {
        return discountPct;
    }

    public Terms discountPct(Double discountPct) {
        this.discountPct = discountPct;
        return this;
    }

    public void setDiscountPct(Double discountPct) {
        this.discountPct = discountPct;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Terms customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Terms addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setTerms(this);
        return this;
    }

    public Terms removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setTerms(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Terms invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Terms addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setTerms(this);
        return this;
    }

    public Terms removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setTerms(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Terms)) {
            return false;
        }
        return id != null && id.equals(((Terms) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Terms{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", dayOfMonthDue=" + getDayOfMonthDue() +
            ", dueNextMonthDays=" + getDueNextMonthDays() +
            ", discountDayOfMonth=" + getDiscountDayOfMonth() +
            ", discountPct=" + getDiscountPct() +
            "}";
    }
}
