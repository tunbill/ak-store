package com.ak.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A JobType.
 */
@Entity
@Table(name = "job_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "jobType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Jobs> jobs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("jobTypes")
    private Company company;

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

    public JobType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public JobType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Jobs> getJobs() {
        return jobs;
    }

    public JobType jobs(Set<Jobs> jobs) {
        this.jobs = jobs;
        return this;
    }

    public JobType addJobs(Jobs jobs) {
        this.jobs.add(jobs);
        jobs.setJobType(this);
        return this;
    }

    public JobType removeJobs(Jobs jobs) {
        this.jobs.remove(jobs);
        jobs.setJobType(null);
        return this;
    }

    public void setJobs(Set<Jobs> jobs) {
        this.jobs = jobs;
    }

    public Company getCompany() {
        return company;
    }

    public JobType company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobType)) {
            return false;
        }
        return id != null && id.equals(((JobType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JobType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
