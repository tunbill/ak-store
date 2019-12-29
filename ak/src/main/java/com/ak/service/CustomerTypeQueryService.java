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

import com.ak.domain.CustomerType;
import com.ak.domain.*; // for static metamodels
import com.ak.repository.CustomerTypeRepository;
import com.ak.service.dto.CustomerTypeCriteria;

/**
 * Service for executing complex queries for {@link CustomerType} entities in the database.
 * The main input is a {@link CustomerTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerType} or a {@link Page} of {@link CustomerType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerTypeQueryService extends QueryService<CustomerType> {

    private final Logger log = LoggerFactory.getLogger(CustomerTypeQueryService.class);

    private final CustomerTypeRepository customerTypeRepository;

    public CustomerTypeQueryService(CustomerTypeRepository customerTypeRepository) {
        this.customerTypeRepository = customerTypeRepository;
    }

    /**
     * Return a {@link List} of {@link CustomerType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerType> findByCriteria(CustomerTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerType> specification = createSpecification(criteria);
        return customerTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CustomerType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerType> findByCriteria(CustomerTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerType> specification = createSpecification(criteria);
        return customerTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerType> specification = createSpecification(criteria);
        return customerTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerType> createSpecification(CustomerTypeCriteria criteria) {
        Specification<CustomerType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerType_.id));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), CustomerType_.companyId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CustomerType_.description));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), CustomerType_.isActive));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(CustomerType_.customers, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
