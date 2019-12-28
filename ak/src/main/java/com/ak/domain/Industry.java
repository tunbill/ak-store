package com.ak.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Industry.
 */
@Entity
@Table(name = "industry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Industry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "industry")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Company> companies = new HashSet<>();

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

    public Industry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Industry companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Industry addCompany(Company company) {
        this.companies.add(company);
        company.setIndustry(this);
        return this;
    }

    public Industry removeCompany(Company company) {
        this.companies.remove(company);
        company.setIndustry(null);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Industry)) {
            return false;
        }
        return id != null && id.equals(((Industry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Industry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
