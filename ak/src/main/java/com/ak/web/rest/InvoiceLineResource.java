package com.ak.web.rest;

import com.ak.domain.InvoiceLine;
import com.ak.service.InvoiceLineService;
import com.ak.web.rest.errors.BadRequestAlertException;
import com.ak.service.dto.InvoiceLineCriteria;
import com.ak.service.InvoiceLineQueryService;

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
 * REST controller for managing {@link com.ak.domain.InvoiceLine}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLineResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineResource.class);

    private static final String ENTITY_NAME = "invoiceLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceLineService invoiceLineService;

    private final InvoiceLineQueryService invoiceLineQueryService;

    public InvoiceLineResource(InvoiceLineService invoiceLineService, InvoiceLineQueryService invoiceLineQueryService) {
        this.invoiceLineService = invoiceLineService;
        this.invoiceLineQueryService = invoiceLineQueryService;
    }

    /**
     * {@code POST  /invoice-lines} : Create a new invoiceLine.
     *
     * @param invoiceLine the invoiceLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceLine, or with status {@code 400 (Bad Request)} if the invoiceLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-lines")
    public ResponseEntity<InvoiceLine> createInvoiceLine(@Valid @RequestBody InvoiceLine invoiceLine) throws URISyntaxException {
        log.debug("REST request to save InvoiceLine : {}", invoiceLine);
        if (invoiceLine.getId() != null) {
            throw new BadRequestAlertException("A new invoiceLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceLine result = invoiceLineService.save(invoiceLine);
        return ResponseEntity.created(new URI("/api/invoice-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-lines} : Updates an existing invoiceLine.
     *
     * @param invoiceLine the invoiceLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceLine,
     * or with status {@code 400 (Bad Request)} if the invoiceLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-lines")
    public ResponseEntity<InvoiceLine> updateInvoiceLine(@Valid @RequestBody InvoiceLine invoiceLine) throws URISyntaxException {
        log.debug("REST request to update InvoiceLine : {}", invoiceLine);
        if (invoiceLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceLine result = invoiceLineService.save(invoiceLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-lines} : get all the invoiceLines.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceLines in body.
     */
    @GetMapping("/invoice-lines")
    public ResponseEntity<List<InvoiceLine>> getAllInvoiceLines(InvoiceLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InvoiceLines by criteria: {}", criteria);
        Page<InvoiceLine> page = invoiceLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /invoice-lines/count} : count all the invoiceLines.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/invoice-lines/count")
    public ResponseEntity<Long> countInvoiceLines(InvoiceLineCriteria criteria) {
        log.debug("REST request to count InvoiceLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoice-lines/:id} : get the "id" invoiceLine.
     *
     * @param id the id of the invoiceLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-lines/{id}")
    public ResponseEntity<InvoiceLine> getInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLine : {}", id);
        Optional<InvoiceLine> invoiceLine = invoiceLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceLine);
    }

    /**
     * {@code DELETE  /invoice-lines/:id} : delete the "id" invoiceLine.
     *
     * @param id the id of the invoiceLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-lines/{id}")
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLine : {}", id);
        invoiceLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
