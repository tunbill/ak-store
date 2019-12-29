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
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.ak.domain.Company} entity. This class is used
 * in {@link com.ak.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private StringFilter email;

    private LocalDateFilter startDate;

    private IntegerFilter numOfUsers;

    private StringFilter type;

    private BooleanFilter isActive;

    private InstantFilter timeCreated;

    private InstantFilter timeModified;

    private LongFilter userId;

    private LongFilter industryId;

    private LongFilter provinceId;

    public CompanyCriteria(){
    }

    public CompanyCriteria(CompanyCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.numOfUsers = other.numOfUsers == null ? null : other.numOfUsers.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.timeCreated = other.timeCreated == null ? null : other.timeCreated.copy();
        this.timeModified = other.timeModified == null ? null : other.timeModified.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.industryId = other.industryId == null ? null : other.industryId.copy();
        this.provinceId = other.provinceId == null ? null : other.provinceId.copy();
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public IntegerFilter getNumOfUsers() {
        return numOfUsers;
    }

    public void setNumOfUsers(IntegerFilter numOfUsers) {
        this.numOfUsers = numOfUsers;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getIndustryId() {
        return industryId;
    }

    public void setIndustryId(LongFilter industryId) {
        this.industryId = industryId;
    }

    public LongFilter getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(LongFilter provinceId) {
        this.provinceId = provinceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyCriteria that = (CompanyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(email, that.email) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(numOfUsers, that.numOfUsers) &&
            Objects.equals(type, that.type) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(timeCreated, that.timeCreated) &&
            Objects.equals(timeModified, that.timeModified) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(industryId, that.industryId) &&
            Objects.equals(provinceId, that.provinceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        address,
        email,
        startDate,
        numOfUsers,
        type,
        isActive,
        timeCreated,
        timeModified,
        userId,
        industryId,
        provinceId
        );
    }

    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (numOfUsers != null ? "numOfUsers=" + numOfUsers + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (timeCreated != null ? "timeCreated=" + timeCreated + ", " : "") +
                (timeModified != null ? "timeModified=" + timeModified + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (industryId != null ? "industryId=" + industryId + ", " : "") +
                (provinceId != null ? "provinceId=" + provinceId + ", " : "") +
            "}";
    }

}
