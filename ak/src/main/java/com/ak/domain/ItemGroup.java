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
 * A ItemGroup.
 */
@Entity
@Table(name = "item_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(name = "code", length = 10)
    private String code;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @OneToMany(mappedBy = "itemGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("itemGroups")
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

    public ItemGroup code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ItemGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public ItemGroup items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public ItemGroup addItem(Item item) {
        this.items.add(item);
        item.setItemGroup(this);
        return this;
    }

    public ItemGroup removeItem(Item item) {
        this.items.remove(item);
        item.setItemGroup(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Company getCompany() {
        return company;
    }

    public ItemGroup company(Company company) {
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
        if (!(o instanceof ItemGroup)) {
            return false;
        }
        return id != null && id.equals(((ItemGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemGroup{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
