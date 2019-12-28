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
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "code", length = 10)
    private String code;

    @NotNull
    @Size(max = 10)
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @OneToMany(mappedBy = "unit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("units")
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

    public Unit code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Unit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Unit items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Unit addItem(Item item) {
        this.items.add(item);
        item.setUnit(this);
        return this;
    }

    public Unit removeItem(Item item) {
        this.items.remove(item);
        item.setUnit(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Company getCompany() {
        return company;
    }

    public Unit company(Company company) {
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
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
