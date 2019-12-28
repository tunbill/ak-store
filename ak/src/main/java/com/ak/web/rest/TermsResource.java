package com.ak.web.rest;

import com.ak.domain.Terms;
import com.ak.repository.TermsRepository;
import com.ak.web.rest.errors.BadRequestAlertException;

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
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ak.domain.Terms}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TermsResource {

    private final Logger log = LoggerFactory.getLogger(TermsResource.class);

    private static final String ENTITY_NAME = "terms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermsRepository termsRepository;

    public TermsResource(TermsRepository termsRepository) {
        this.termsRepository = termsRepository;
    }

    /**
     * {@code POST  /terms} : Create a new terms.
     *
     * @param terms the terms to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terms, or with status {@code 400 (Bad Request)} if the terms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terms")
    public ResponseEntity<Terms> createTerms(@Valid @RequestBody Terms terms) throws URISyntaxException {
        log.debug("REST request to save Terms : {}", terms);
        if (terms.getId() != null) {
            throw new BadRequestAlertException("A new terms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Terms result = termsRepository.save(terms);
        return ResponseEntity.created(new URI("/api/terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terms} : Updates an existing terms.
     *
     * @param terms the terms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terms,
     * or with status {@code 400 (Bad Request)} if the terms is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terms")
    public ResponseEntity<Terms> updateTerms(@Valid @RequestBody Terms terms) throws URISyntaxException {
        log.debug("REST request to update Terms : {}", terms);
        if (terms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Terms result = termsRepository.save(terms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terms.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terms} : get all the terms.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terms in body.
     */
    @GetMapping("/terms")
    public ResponseEntity<List<Terms>> getAllTerms(Pageable pageable) {
        log.debug("REST request to get a page of Terms");
        Page<Terms> page = termsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terms/:id} : get the "id" terms.
     *
     * @param id the id of the terms to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terms, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terms/{id}")
    public ResponseEntity<Terms> getTerms(@PathVariable Long id) {
        log.debug("REST request to get Terms : {}", id);
        Optional<Terms> terms = termsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(terms);
    }

    /**
     * {@code DELETE  /terms/:id} : delete the "id" terms.
     *
     * @param id the id of the terms to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terms/{id}")
    public ResponseEntity<Void> deleteTerms(@PathVariable Long id) {
        log.debug("REST request to delete Terms : {}", id);
        termsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
