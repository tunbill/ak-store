package com.ak.service;

import com.ak.domain.CustomerType;
import com.ak.repository.CustomerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerType}.
 */
@Service
@Transactional
public class CustomerTypeService {

    private final Logger log = LoggerFactory.getLogger(CustomerTypeService.class);

    private final CustomerTypeRepository customerTypeRepository;

    public CustomerTypeService(CustomerTypeRepository customerTypeRepository) {
        this.customerTypeRepository = customerTypeRepository;
    }

    /**
     * Save a customerType.
     *
     * @param customerType the entity to save.
     * @return the persisted entity.
     */
    public CustomerType save(CustomerType customerType) {
        log.debug("Request to save CustomerType : {}", customerType);
        return customerTypeRepository.save(customerType);
    }

    /**
     * Get all the customerTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerType> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerTypes");
        return customerTypeRepository.findAll(pageable);
    }


    /**
     * Get one customerType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerType> findOne(Long id) {
        log.debug("Request to get CustomerType : {}", id);
        return customerTypeRepository.findById(id);
    }

    /**
     * Delete the customerType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerType : {}", id);
        customerTypeRepository.deleteById(id);
    }
}
