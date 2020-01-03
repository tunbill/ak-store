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

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Size(max = 80)
    @Column(name = "full_name", length = 80)
    private String fullName;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Size(max = 20)
    @Column(name = "identity_card", length = 20)
    private String identityCard;

    @Column(name = "identity_date")
    private LocalDate identityDate;

    @Size(max = 100)
    @Column(name = "identity_issue", length = 100)
    private String identityIssue;

    @Size(max = 50)
    @Column(name = "position", length = 50)
    private String position;

    @Size(max = 20)
    @Column(name = "tax_code", length = 20)
    private String taxCode;

    @Column(name = "salary", precision = 21, scale = 2)
    private BigDecimal salary;

    @Column(name = "salary_rate", precision = 21, scale = 2)
    private BigDecimal salaryRate;

    @Column(name = "salary_security", precision = 21, scale = 2)
    private BigDecimal salarySecurity;

    @Column(name = "num_of_depends")
    private Integer numOfDepends;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 20)
    @Column(name = "mobile", length = 20)
    private String mobile;

    @Size(max = 100)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 20)
    @Column(name = "bank_account", length = 20)
    private String bankAccount;

    @Size(max = 150)
    @Column(name = "bank_name", length = 150)
    private String bankName;

    @Size(max = 200)
    @Column(name = "nodes", length = 200)
    private String nodes;

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

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Department department;

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

    public Employee companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCode() {
        return code;
    }

    public Employee code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public Employee fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getSex() {
        return sex;
    }

    public Employee sex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Employee birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public Employee identityCard(String identityCard) {
        this.identityCard = identityCard;
        return this;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public LocalDate getIdentityDate() {
        return identityDate;
    }

    public Employee identityDate(LocalDate identityDate) {
        this.identityDate = identityDate;
        return this;
    }

    public void setIdentityDate(LocalDate identityDate) {
        this.identityDate = identityDate;
    }

    public String getIdentityIssue() {
        return identityIssue;
    }

    public Employee identityIssue(String identityIssue) {
        this.identityIssue = identityIssue;
        return this;
    }

    public void setIdentityIssue(String identityIssue) {
        this.identityIssue = identityIssue;
    }

    public String getPosition() {
        return position;
    }

    public Employee position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public Employee taxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Employee salary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSalaryRate() {
        return salaryRate;
    }

    public Employee salaryRate(BigDecimal salaryRate) {
        this.salaryRate = salaryRate;
        return this;
    }

    public void setSalaryRate(BigDecimal salaryRate) {
        this.salaryRate = salaryRate;
    }

    public BigDecimal getSalarySecurity() {
        return salarySecurity;
    }

    public Employee salarySecurity(BigDecimal salarySecurity) {
        this.salarySecurity = salarySecurity;
        return this;
    }

    public void setSalarySecurity(BigDecimal salarySecurity) {
        this.salarySecurity = salarySecurity;
    }

    public Integer getNumOfDepends() {
        return numOfDepends;
    }

    public Employee numOfDepends(Integer numOfDepends) {
        this.numOfDepends = numOfDepends;
        return this;
    }

    public void setNumOfDepends(Integer numOfDepends) {
        this.numOfDepends = numOfDepends;
    }

    public String getPhone() {
        return phone;
    }

    public Employee phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public Employee mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public Employee bankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public Employee bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNodes() {
        return nodes;
    }

    public Employee nodes(String nodes) {
        this.nodes = nodes;
        return this;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Employee isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public Employee timeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Instant getTimeModified() {
        return timeModified;
    }

    public Employee timeModified(Instant timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    public void setTimeModified(Instant timeModified) {
        this.timeModified = timeModified;
    }

    public Long getUserIdCreated() {
        return userIdCreated;
    }

    public Employee userIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
        return this;
    }

    public void setUserIdCreated(Long userIdCreated) {
        this.userIdCreated = userIdCreated;
    }

    public Long getUserIdModified() {
        return userIdModified;
    }

    public Employee userIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
        return this;
    }

    public void setUserIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Employee invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Employee addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setEmployee(this);
        return this;
    }

    public Employee removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setEmployee(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", code='" + getCode() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", sex=" + getSex() +
            ", birthday='" + getBirthday() + "'" +
            ", identityCard='" + getIdentityCard() + "'" +
            ", identityDate='" + getIdentityDate() + "'" +
            ", identityIssue='" + getIdentityIssue() + "'" +
            ", position='" + getPosition() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", salary=" + getSalary() +
            ", salaryRate=" + getSalaryRate() +
            ", salarySecurity=" + getSalarySecurity() +
            ", numOfDepends=" + getNumOfDepends() +
            ", phone='" + getPhone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", nodes='" + getNodes() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", timeCreated='" + getTimeCreated() + "'" +
            ", timeModified='" + getTimeModified() + "'" +
            ", userIdCreated=" + getUserIdCreated() +
            ", userIdModified=" + getUserIdModified() +
            "}";
    }
}
