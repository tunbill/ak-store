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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.ak.domain.Employee} entity. This class is used
 * in {@link com.ak.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter companyId;

    private StringFilter code;

    private StringFilter fullName;

    private IntegerFilter sex;

    private LocalDateFilter birthday;

    private StringFilter identityCard;

    private LocalDateFilter identityDate;

    private StringFilter identityIssue;

    private StringFilter position;

    private StringFilter taxCode;

    private BigDecimalFilter salary;

    private BigDecimalFilter salaryRate;

    private BigDecimalFilter salarySecurity;

    private IntegerFilter numOfDepends;

    private StringFilter phone;

    private StringFilter mobile;

    private StringFilter email;

    private StringFilter bankAccount;

    private StringFilter bankName;

    private StringFilter nodes;

    private BooleanFilter isActive;

    private InstantFilter timeCreated;

    private InstantFilter timeModified;

    private LongFilter userIdCreated;

    private LongFilter userIdModified;

    private LongFilter invoiceId;

    private LongFilter departmentId;

    public EmployeeCriteria(){
    }

    public EmployeeCriteria(EmployeeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.sex = other.sex == null ? null : other.sex.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.identityCard = other.identityCard == null ? null : other.identityCard.copy();
        this.identityDate = other.identityDate == null ? null : other.identityDate.copy();
        this.identityIssue = other.identityIssue == null ? null : other.identityIssue.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.taxCode = other.taxCode == null ? null : other.taxCode.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.salaryRate = other.salaryRate == null ? null : other.salaryRate.copy();
        this.salarySecurity = other.salarySecurity == null ? null : other.salarySecurity.copy();
        this.numOfDepends = other.numOfDepends == null ? null : other.numOfDepends.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.bankAccount = other.bankAccount == null ? null : other.bankAccount.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.nodes = other.nodes == null ? null : other.nodes.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.timeCreated = other.timeCreated == null ? null : other.timeCreated.copy();
        this.timeModified = other.timeModified == null ? null : other.timeModified.copy();
        this.userIdCreated = other.userIdCreated == null ? null : other.userIdCreated.copy();
        this.userIdModified = other.userIdModified == null ? null : other.userIdModified.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public IntegerFilter getSex() {
        return sex;
    }

    public void setSex(IntegerFilter sex) {
        this.sex = sex;
    }

    public LocalDateFilter getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateFilter birthday) {
        this.birthday = birthday;
    }

    public StringFilter getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(StringFilter identityCard) {
        this.identityCard = identityCard;
    }

    public LocalDateFilter getIdentityDate() {
        return identityDate;
    }

    public void setIdentityDate(LocalDateFilter identityDate) {
        this.identityDate = identityDate;
    }

    public StringFilter getIdentityIssue() {
        return identityIssue;
    }

    public void setIdentityIssue(StringFilter identityIssue) {
        this.identityIssue = identityIssue;
    }

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(StringFilter taxCode) {
        this.taxCode = taxCode;
    }

    public BigDecimalFilter getSalary() {
        return salary;
    }

    public void setSalary(BigDecimalFilter salary) {
        this.salary = salary;
    }

    public BigDecimalFilter getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(BigDecimalFilter salaryRate) {
        this.salaryRate = salaryRate;
    }

    public BigDecimalFilter getSalarySecurity() {
        return salarySecurity;
    }

    public void setSalarySecurity(BigDecimalFilter salarySecurity) {
        this.salarySecurity = salarySecurity;
    }

    public IntegerFilter getNumOfDepends() {
        return numOfDepends;
    }

    public void setNumOfDepends(IntegerFilter numOfDepends) {
        this.numOfDepends = numOfDepends;
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

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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

    public StringFilter getNodes() {
        return nodes;
    }

    public void setNodes(StringFilter nodes) {
        this.nodes = nodes;
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

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(sex, that.sex) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(identityCard, that.identityCard) &&
            Objects.equals(identityDate, that.identityDate) &&
            Objects.equals(identityIssue, that.identityIssue) &&
            Objects.equals(position, that.position) &&
            Objects.equals(taxCode, that.taxCode) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(salaryRate, that.salaryRate) &&
            Objects.equals(salarySecurity, that.salarySecurity) &&
            Objects.equals(numOfDepends, that.numOfDepends) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(email, that.email) &&
            Objects.equals(bankAccount, that.bankAccount) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(nodes, that.nodes) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(timeCreated, that.timeCreated) &&
            Objects.equals(timeModified, that.timeModified) &&
            Objects.equals(userIdCreated, that.userIdCreated) &&
            Objects.equals(userIdModified, that.userIdModified) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyId,
        code,
        fullName,
        sex,
        birthday,
        identityCard,
        identityDate,
        identityIssue,
        position,
        taxCode,
        salary,
        salaryRate,
        salarySecurity,
        numOfDepends,
        phone,
        mobile,
        email,
        bankAccount,
        bankName,
        nodes,
        isActive,
        timeCreated,
        timeModified,
        userIdCreated,
        userIdModified,
        invoiceId,
        departmentId
        );
    }

    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (birthday != null ? "birthday=" + birthday + ", " : "") +
                (identityCard != null ? "identityCard=" + identityCard + ", " : "") +
                (identityDate != null ? "identityDate=" + identityDate + ", " : "") +
                (identityIssue != null ? "identityIssue=" + identityIssue + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (taxCode != null ? "taxCode=" + taxCode + ", " : "") +
                (salary != null ? "salary=" + salary + ", " : "") +
                (salaryRate != null ? "salaryRate=" + salaryRate + ", " : "") +
                (salarySecurity != null ? "salarySecurity=" + salarySecurity + ", " : "") +
                (numOfDepends != null ? "numOfDepends=" + numOfDepends + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (bankAccount != null ? "bankAccount=" + bankAccount + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (nodes != null ? "nodes=" + nodes + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (timeCreated != null ? "timeCreated=" + timeCreated + ", " : "") +
                (timeModified != null ? "timeModified=" + timeModified + ", " : "") +
                (userIdCreated != null ? "userIdCreated=" + userIdCreated + ", " : "") +
                (userIdModified != null ? "userIdModified=" + userIdModified + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            "}";
    }

}
