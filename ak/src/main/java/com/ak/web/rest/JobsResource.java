package com.ak.web.rest;

import com.ak.domain.Jobs;
import com.ak.service.JobsService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ak.domain.Jobs}.
 */
@RestController
@RequestMapping("/api")
public class JobsResource {

    private final Logger log = LoggerFactory.getLogger(JobsResource.class);

    private static final String ENTITY_NAME = "jobs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobsService jobsService;

    public JobsResource(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    /**
     * {@code POST  /jobs} : Create a new jobs.
     *
     * @param jobs the jobs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobs, or with status {@code 400 (Bad Request)} if the jobs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jobs")
    public ResponseEntity<Jobs> createJobs(@Valid @RequestBody Jobs jobs) throws URISyntaxException {
        log.debug("REST request to save Jobs : {}", jobs);
        if (jobs.getId() != null) {
            throw new BadRequestAlertException("A new jobs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jobs result = jobsService.save(jobs);
        return ResponseEntity.created(new URI("/api/jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jobs} : Updates an existing jobs.
     *
     * @param jobs the jobs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobs,
     * or with status {@code 400 (Bad Request)} if the jobs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jobs")
    public ResponseEntity<Jobs> updateJobs(@Valid @RequestBody Jobs jobs) throws URISyntaxException {
        log.debug("REST request to update Jobs : {}", jobs);
        if (jobs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jobs result = jobsService.save(jobs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jobs} : get all the jobs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobs in body.
     */
    @GetMapping("/jobs")
    public ResponseEntity<List<Jobs>> getAllJobs(Pageable pageable) {
        log.debug("REST request to get a page of Jobs");
        Page<Jobs> page = jobsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jobs/:id} : get the "id" jobs.
     *
     * @param id the id of the jobs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Jobs> getJobs(@PathVariable Long id) {
        log.debug("REST request to get Jobs : {}", id);
        Optional<Jobs> jobs = jobsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobs);
    }

    /**
     * {@code DELETE  /jobs/:id} : delete the "id" jobs.
     *
     * @param id the id of the jobs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJobs(@PathVariable Long id) {
        log.debug("REST request to delete Jobs : {}", id);
        jobsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
