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

import com.ak.domain.Item;
import com.ak.domain.*; // for static metamodels
import com.ak.repository.ItemRepository;
import com.ak.service.dto.ItemCriteria;

/**
 * Service for executing complex queries for {@link Item} entities in the database.
 * The main input is a {@link ItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Item} or a {@link Page} of {@link Item} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemQueryService extends QueryService<Item> {

    private final Logger log = LoggerFactory.getLogger(ItemQueryService.class);

    private final ItemRepository itemRepository;

    public ItemQueryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Return a {@link List} of {@link Item} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Item> findByCriteria(ItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Item> specification = createSpecification(criteria);
        return itemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Item} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Item> findByCriteria(ItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Item> specification = createSpecification(criteria);
        return itemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Item> specification = createSpecification(criteria);
        return itemRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Item> createSpecification(ItemCriteria criteria) {
        Specification<Item> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Item_.id));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Item_.companyId));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Item_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Item_.name)
                		.or(buildStringSpecification(criteria.getName(), Item_.code))
                		);
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Item_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Item_.type));
            }
            if (criteria.getSkuNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSkuNum(), Item_.skuNum));
            }
            if (criteria.getVatTax() != null) {
                specification = specification.and(buildSpecification(criteria.getVatTax(), Item_.vatTax));
            }
            if (criteria.getImportTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImportTax(), Item_.importTax));
            }
            if (criteria.getExportTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExportTax(), Item_.exportTax));
            }
            if (criteria.getInventoryPriceMethod() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryPriceMethod(), Item_.inventoryPriceMethod));
            }
            if (criteria.getAccountItem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountItem(), Item_.accountItem));
            }
            if (criteria.getIsAllowModified() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAllowModified(), Item_.isAllowModified));
            }
            if (criteria.getAccountCost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountCost(), Item_.accountCost));
            }
            if (criteria.getAccountRevenue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountRevenue(), Item_.accountRevenue));
            }
            if (criteria.getAccountInternalRevenue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountInternalRevenue(), Item_.accountInternalRevenue));
            }
            if (criteria.getAccountSaleReturn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountSaleReturn(), Item_.accountSaleReturn));
            }
            if (criteria.getAccountSalePrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountSalePrice(), Item_.accountSalePrice));
            }
            if (criteria.getAccountAgency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountAgency(), Item_.accountAgency));
            }
            if (criteria.getAccountRawProduct() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountRawProduct(), Item_.accountRawProduct));
            }
            if (criteria.getAccountCostDifference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountCostDifference(), Item_.accountCostDifference));
            }
            if (criteria.getAccountDiscount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountDiscount(), Item_.accountDiscount));
            }
            if (criteria.getSaleDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSaleDesc(), Item_.saleDesc));
            }
            if (criteria.getPurchaseDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurchaseDesc(), Item_.purchaseDesc));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Item_.weight));
            }
            if (criteria.getLenght() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLenght(), Item_.lenght));
            }
            if (criteria.getWide() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWide(), Item_.wide));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Item_.height));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), Item_.color));
            }
            if (criteria.getSpecification() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecification(), Item_.specification));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Item_.isActive));
            }
            if (criteria.getTimeCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeCreated(), Item_.timeCreated));
            }
            if (criteria.getTimeModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeModified(), Item_.timeModified));
            }
            if (criteria.getUserIdCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserIdCreated(), Item_.userIdCreated));
            }
            if (criteria.getUserIdModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserIdModified(), Item_.userIdModified));
            }
            if (criteria.getInvoiceLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceLineId(),
                    root -> root.join(Item_.invoiceLines, JoinType.LEFT).get(InvoiceLine_.id)));
            }
            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(Item_.unit, JoinType.LEFT).get(Unit_.id)));
            }
            if (criteria.getItemGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemGroupId(),
                    root -> root.join(Item_.itemGroup, JoinType.LEFT).get(ItemGroup_.id)));
            }
            if (criteria.getStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getStoreId(),
                    root -> root.join(Item_.store, JoinType.LEFT).get(Store_.id)));
            }
        }
        return specification;
    }
}
