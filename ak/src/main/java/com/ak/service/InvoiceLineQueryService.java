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

import com.ak.domain.InvoiceLine;
import com.ak.domain.*; // for static metamodels
import com.ak.repository.InvoiceLineRepository;
import com.ak.service.dto.InvoiceLineCriteria;

/**
 * Service for executing complex queries for {@link InvoiceLine} entities in the database.
 * The main input is a {@link InvoiceLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceLine} or a {@link Page} of {@link InvoiceLine} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceLineQueryService extends QueryService<InvoiceLine> {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineQueryService.class);

    private final InvoiceLineRepository invoiceLineRepository;

    public InvoiceLineQueryService(InvoiceLineRepository invoiceLineRepository) {
        this.invoiceLineRepository = invoiceLineRepository;
    }

    /**
     * Return a {@link List} of {@link InvoiceLine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceLine> findByCriteria(InvoiceLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvoiceLine> specification = createSpecification(criteria);
        return invoiceLineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InvoiceLine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceLine> findByCriteria(InvoiceLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceLine> specification = createSpecification(criteria);
        return invoiceLineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvoiceLine> specification = createSpecification(criteria);
        return invoiceLineRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceLine> createSpecification(InvoiceLineCriteria criteria) {
        Specification<InvoiceLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceLine_.id));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), InvoiceLine_.companyId));
            }
            if (criteria.getDisplayOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisplayOrder(), InvoiceLine_.displayOrder));
            }
            if (criteria.getItemName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemName(), InvoiceLine_.itemName));
            }
            if (criteria.getUnitName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitName(), InvoiceLine_.unitName));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), InvoiceLine_.quantity));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), InvoiceLine_.rate));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), InvoiceLine_.amount));
            }
            if (criteria.getDiscountPct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountPct(), InvoiceLine_.discountPct));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), InvoiceLine_.accountNumber));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), InvoiceLine_.status));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(InvoiceLine_.invoice, JoinType.LEFT).get(Invoice_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(InvoiceLine_.item, JoinType.LEFT).get(Item_.id)));
            }
        }
        return specification;
    }
}
