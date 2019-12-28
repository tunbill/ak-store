package com.ak.web.rest;

import com.ak.domain.Industry;
import com.ak.repository.IndustryRepository;
import com.ak.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ak.domain.Industry}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);

    private static final String ENTITY_NAME = "industry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndustryRepository industryRepository;

    public IndustryResource(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    /**
     * {@code POST  /industries} : Create a new industry.
     *
     * @param industry the industry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new industry, or with status {@code 400 (Bad Request)} if the industry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/industries")
    public ResponseEntity<Industry> createIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industry);
        if (industry.getId() != null) {
            throw new BadRequestAlertException("A new industry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Industry result = industryRepository.save(industry);
        return ResponseEntity.created(new URI("/api/industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /industries} : Updates an existing industry.
     *
     * @param industry the industry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industry,
     * or with status {@code 400 (Bad Request)} if the industry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the industry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/industries")
    public ResponseEntity<Industry> updateIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to update Industry : {}", industry);
        if (industry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Industry result = industryRepository.save(industry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, industry.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /industries} : get all the industries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of industries in body.
     */
    @GetMapping("/industries")
    public List<Industry> getAllIndustries() {
        log.debug("REST request to get all Industries");
        return industryRepository.findAll();
    }

    /**
     * {@code GET  /industries/:id} : get the "id" industry.
     *
     * @param id the id of the industry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the industry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/industries/{id}")
    public ResponseEntity<Industry> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Optional<Industry> industry = industryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(industry);
    }

    /**
     * {@code DELETE  /industries/:id} : delete the "id" industry.
     *
     * @param id the id of the industry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/industries/{id}")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
