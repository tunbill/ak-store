package com.ak.service;

import com.ak.domain.ItemGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ItemGroup}.
 */
public interface ItemGroupService {

    /**
     * Save a itemGroup.
     *
     * @param itemGroup the entity to save.
     * @return the persisted entity.
     */
    ItemGroup save(ItemGroup itemGroup);

    /**
     * Get all the itemGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemGroup> findAll(Pageable pageable);

    /**
     * Get all the itemGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemGroup> findByCompanyId(Long companyId, Pageable pageable);
    /**
     * Get the "id" itemGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemGroup> findOne(Long id);

    /**
     * Delete the "id" itemGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
