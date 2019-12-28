package com.ak.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ak.domain.Company;
import com.ak.domain.*; // for static metamodels
import com.ak.repository.CompanyRepository;
import com.ak.service.dto.CompanyCriteria;

/**
 * Service for executing complex queries for {@link Company} entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Company} or a {@link Page} of {@link Company} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

    private final CompanyRepository companyRepository;

    public CompanyQueryService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Return a {@link List} of {@link Company} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Company> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Company} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Company> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Company> createSpecification(CompanyCriteria criteria) {
        Specification<Company> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Company_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Company_.address));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Company_.email));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Company_.startDate));
            }
            if (criteria.getNumOfUsers() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumOfUsers(), Company_.numOfUsers));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Company_.type));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Company_.isActive));
            }
            if (criteria.getTimeCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeCreated(), Company_.timeCreated));
            }
            if (criteria.getTimeModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeModified(), Company_.timeModified));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), Company_.userId));
            }
            if (criteria.getCustomerTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerTypeId(),
                    root -> root.join(Company_.customerTypes, JoinType.LEFT).get(CustomerType_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Company_.customers, JoinType.LEFT).get(Customer_.id)));
            }
            if (criteria.getStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getStoreId(),
                    root -> root.join(Company_.stores, JoinType.LEFT).get(Store_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(Company_.departments, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getJobsId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobsId(),
                    root -> root.join(Company_.jobs, JoinType.LEFT).get(Jobs_.id)));
            }
            if (criteria.getJobTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobTypeId(),
                    root -> root.join(Company_.jobTypes, JoinType.LEFT).get(JobType_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Company_.employees, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(Company_.items, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getItemGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemGroupId(),
                    root -> root.join(Company_.itemGroups, JoinType.LEFT).get(ItemGroup_.id)));
            }
            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(Company_.units, JoinType.LEFT).get(Unit_.id)));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(Company_.invoices, JoinType.LEFT).get(Invoice_.id)));
            }
            if (criteria.getInvoiceLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceLineId(),
                    root -> root.join(Company_.invoiceLines, JoinType.LEFT).get(InvoiceLine_.id)));
            }
            if (criteria.getIndustryId() != null) {
                specification = specification.and(buildSpecification(criteria.getIndustryId(),
                    root -> root.join(Company_.industry, JoinType.LEFT).get(Industry_.id)));
            }
            if (criteria.getProvinceId() != null) {
                specification = specification.and(buildSpecification(criteria.getProvinceId(),
                    root -> root.join(Company_.province, JoinType.LEFT).get(Province_.id)));
            }
        }
        return specification;
    }
}
