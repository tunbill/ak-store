package com.ak.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Size(max = 50)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Min(value = 1)
    @Column(name = "num_of_users")
    private Integer numOfUsers;

    @Size(max = 10)
    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "time_created")
    private Instant timeCreated;

    @Column(name = "time_modified")
    private Instant timeModified;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerType> customerTypes = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Department> departments = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Jobs> jobs = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobType> jobTypes = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemGroup> itemGroups = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Unit> units = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("companies")
    private Industry industry;

    @ManyToOne
    @JsonIgnoreProperties("companies")
    private Province province;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Company logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Company logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Company startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getNumOfUsers() {
        return numOfUsers;
    }

    public Company numOfUsers(Integer numOfUsers) {
        this.numOfUsers = numOfUsers;
        return this;
    }

    public void setNumOfUsers(Integer numOfUsers) {
        this.numOfUsers = numOfUsers;
    }

    public String getType() {
        return type;
    }

    public Company type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Company isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public Company timeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Instant getTimeModified() {
        return timeModified;
    }

    public Company timeModified(Instant timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(Instant timeModified) {
        this.timeModified = timeModified;
    }

    public Long getUserId() {
        return userId;
    }

    public Company userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<CustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public Company customerTypes(Set<CustomerType> customerTypes) {
        this.customerTypes = customerTypes;
        return this;
    }

    public Company addCustomerType(CustomerType customerType) {
        this.customerTypes.add(customerType);
        customerType.setCompany(this);
        return this;
    }

    public Company removeCustomerType(CustomerType customerType) {
        this.customerTypes.remove(customerType);
        customerType.setCompany(null);
        return this;
    }

    public void setCustomerTypes(Set<CustomerType> customerTypes) {
        this.customerTypes = customerTypes;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Company customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Company addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setCompany(this);
        return this;
    }

    public Company removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setCompany(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Company stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Company addStore(Store store) {
        this.stores.add(store);
        store.setCompany(this);
        return this;
    }

    public Company removeStore(Store store) {
        this.stores.remove(store);
        store.setCompany(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Company departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Company addDepartment(Department department) {
        this.departments.add(department);
        department.setCompany(this);
        return this;
    }

    public Company removeDepartment(Department department) {
        this.departments.remove(department);
        department.setCompany(null);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<Jobs> getJobs() {
        return jobs;
    }

    public Company jobs(Set<Jobs> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Company addJobs(Jobs jobs) {
        this.jobs.add(jobs);
        jobs.setCompany(this);
        return this;
    }

    public Company removeJobs(Jobs jobs) {
        this.jobs.remove(jobs);
        jobs.setCompany(null);
        return this;
    }

    public void setJobs(Set<Jobs> jobs) {
        this.jobs = jobs;
    }

    public Set<JobType> getJobTypes() {
        return jobTypes;
    }

    public Company jobTypes(Set<JobType> jobTypes) {
        this.jobTypes = jobTypes;
        return this;
    }

    public Company addJobType(JobType jobType) {
        this.jobTypes.add(jobType);
        jobType.setCompany(this);
        return this;
    }

    public Company removeJobType(JobType jobType) {
        this.jobTypes.remove(jobType);
        jobType.setCompany(null);
        return this;
    }

    public void setJobTypes(Set<JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Company employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Company addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setCompany(this);
        return this;
    }

    public Company removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setCompany(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Company items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Company addItem(Item item) {
        this.items.add(item);
        item.setCompany(this);
        return this;
    }

    public Company removeItem(Item item) {
        this.items.remove(item);
        item.setCompany(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public Company itemGroups(Set<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
        return this;
    }

    public Company addItemGroup(ItemGroup itemGroup) {
        this.itemGroups.add(itemGroup);
        itemGroup.setCompany(this);
        return this;
    }

    public Company removeItemGroup(ItemGroup itemGroup) {
        this.itemGroups.remove(itemGroup);
        itemGroup.setCompany(null);
        return this;
    }

    public void setItemGroups(Set<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public Company units(Set<Unit> units) {
        this.units = units;
        return this;
    }

    public Company addUnit(Unit unit) {
        this.units.add(unit);
        unit.setCompany(this);
        return this;
    }

    public Company removeUnit(Unit unit) {
        this.units.remove(unit);
        unit.setCompany(null);
        return this;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Company invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Company addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCompany(this);
        return this;
    }

    public Company removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCompany(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public Company invoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
        return this;
    }

    public Company addInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLines.add(invoiceLine);
        invoiceLine.setCompany(this);
        return this;
    }

    public Company removeInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLines.remove(invoiceLine);
        invoiceLine.setCompany(null);
        return this;
    }

    public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public Industry getIndustry() {
        return industry;
    }

    public Company industry(Industry industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Province getProvince() {
        return province;
    }

    public Company province(Province province) {
        this.province = province;
        return this;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", numOfUsers=" + getNumOfUsers() +
            ", type='" + getType() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", timeCreated='" + getTimeCreated() + "'" +
            ", timeModified='" + getTimeModified() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
