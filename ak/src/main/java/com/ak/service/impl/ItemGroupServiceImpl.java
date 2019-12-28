package com.ak.service.impl;

import com.ak.service.ItemGroupService;
import com.ak.domain.ItemGroup;
import com.ak.repository.ItemGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemGroup}.
 */
@Service
@Transactional
public class ItemGroupServiceImpl implements ItemGroupService {

    private final Logger log = LoggerFactory.getLogger(ItemGroupServiceImpl.class);

    private final ItemGroupRepository itemGroupRepository;

    public ItemGroupServiceImpl(ItemGroupRepository itemGroupRepository) {
        this.itemGroupRepository = itemGroupRepository;
    }

    /**
     * Save a itemGroup.
     *
     * @param itemGroup the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemGroup save(ItemGroup itemGroup) {
        log.debug("Request to save ItemGroup : {}", itemGroup);
        return itemGroupRepository.save(itemGroup);
    }

    /**
     * Get all the itemGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemGroup> findAll(Pageable pageable) {
        log.debug("Request to get all ItemGroups");
        return itemGroupRepository.findAll(pageable);
    }


    /**
     * Get one itemGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemGroup> findOne(Long id) {
        log.debug("Request to get ItemGroup : {}", id);
        return itemGroupRepository.findById(id);
    }

    /**
     * Delete the itemGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemGroup : {}", id);
        itemGroupRepository.deleteById(id);
    }
}
