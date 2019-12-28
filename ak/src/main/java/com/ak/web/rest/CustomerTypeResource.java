package com.ak.web.rest;

import com.ak.domain.CustomerType;
import com.ak.service.CustomerTypeService;
import com.ak.web.rest.errors.BadRequestAlertException;
import com.ak.service.dto.CustomerTypeCriteria;
import com.ak.service.CustomerTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ak.domain.CustomerType}.
 */
@RestController
@RequestMapping("/api")
public class CustomerTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTypeResource.class);

    private static final String ENTITY_NAME = "customerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerTypeService customerTypeService;

    private final CustomerTypeQueryService customerTypeQueryService;

    public CustomerTypeResource(CustomerTypeService customerTypeService, CustomerTypeQueryService customerTypeQueryService) {
        this.customerTypeService = customerTypeService;
        this.customerTypeQueryService = customerTypeQueryService;
    }

    /**
     * {@code POST  /customer-types} : Create a new customerType.
     *
     * @param customerType the customerType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerType, or with status {@code 400 (Bad Request)} if the customerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-types")
    public ResponseEntity<CustomerType> createCustomerType(@Valid @RequestBody CustomerType customerType) throws URISyntaxException {
        log.debug("REST request to save CustomerType : {}", customerType);
        if (customerType.getId() != null) {
            throw new BadRequestAlertException("A new customerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerType result = customerTypeService.save(customerType);
        return ResponseEntity.created(new URI("/api/customer-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-types} : Updates an existing customerType.
     *
     * @param customerType the customerType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerType,
     * or with status {@code 400 (Bad Request)} if the customerType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-types")
    public ResponseEntity<CustomerType> updateCustomerType(@Valid @RequestBody CustomerType customerType) throws URISyntaxException {
        log.debug("REST request to update CustomerType : {}", customerType);
        if (customerType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerType result = customerTypeService.save(customerType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-types} : get all the customerTypes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerTypes in body.
     */
    @GetMapping("/customer-types")
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes(CustomerTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CustomerTypes by criteria: {}", criteria);
        Page<CustomerType> page = customerTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /customer-types/count} : count all the customerTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/customer-types/count")
    public ResponseEntity<Long> countCustomerTypes(CustomerTypeCriteria criteria) {
        log.debug("REST request to count CustomerTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-types/:id} : get the "id" customerType.
     *
     * @param id the id of the customerType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-types/{id}")
    public ResponseEntity<CustomerType> getCustomerType(@PathVariable Long id) {
        log.debug("REST request to get CustomerType : {}", id);
        Optional<CustomerType> customerType = customerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerType);
    }

    /**
     * {@code DELETE  /customer-types/:id} : delete the "id" customerType.
     *
     * @param id the id of the customerType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-types/{id}")
    public ResponseEntity<Void> deleteCustomerType(@PathVariable Long id) {
        log.debug("REST request to delete CustomerType : {}", id);
        customerTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
