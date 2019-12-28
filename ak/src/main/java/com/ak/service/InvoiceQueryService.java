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

import com.ak.domain.Invoice;
import com.ak.domain.*; // for static metamodels
import com.ak.repository.InvoiceRepository;
import com.ak.service.dto.InvoiceCriteria;

/**
 * Service for executing complex queries for {@link Invoice} entities in the database.
 * The main input is a {@link InvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Invoice} or a {@link Page} of {@link Invoice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceQueryService extends QueryService<Invoice> {

    private final Logger log = LoggerFactory.getLogger(InvoiceQueryService.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Return a {@link List} of {@link Invoice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Invoice> findByCriteria(InvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Invoice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Invoice> findByCriteria(InvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Invoice> createSpecification(InvoiceCriteria criteria) {
        Specification<Invoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Invoice_.id));
            }
            if (criteria.getInvoiceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNo(), Invoice_.invoiceNo));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), Invoice_.invoiceDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Invoice_.dueDate));
            }
            if (criteria.getBillingAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddress(), Invoice_.billingAddress));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), Invoice_.accountNumber));
            }
            if (criteria.getPoNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPoNumber(), Invoice_.poNumber));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Invoice_.notes));
            }
            if (criteria.getProductTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductTotal(), Invoice_.productTotal));
            }
            if (criteria.getVatTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVatTotal(), Invoice_.vatTotal));
            }
            if (criteria.getDiscountTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountTotal(), Invoice_.discountTotal));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Invoice_.total));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Invoice_.status));
            }
            if (criteria.getTimeCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeCreated(), Invoice_.timeCreated));
            }
            if (criteria.getTimeModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeModified(), Invoice_.timeModified));
            }
            if (criteria.getUserIdCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserIdCreated(), Invoice_.userIdCreated));
            }
            if (criteria.getUserIdModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserIdModified(), Invoice_.userIdModified));
            }
            if (criteria.getInvoiceLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceLineId(),
                    root -> root.join(Invoice_.invoiceLines, JoinType.LEFT).get(InvoiceLine_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Invoice_.customer, JoinType.LEFT).get(Customer_.id)));
            }
            if (criteria.getTermsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTermsId(),
                    root -> root.join(Invoice_.terms, JoinType.LEFT).get(Terms_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Invoice_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(Invoice_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
