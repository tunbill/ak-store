package com.ak.web.rest;

import com.ak.domain.JobType;
import com.ak.service.JobTypeService;
import com.ak.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ak.domain.JobType}.
 */
@RestController
@RequestMapping("/api")
public class JobTypeResource {

    private final Logger log = LoggerFactory.getLogger(JobTypeResource.class);

    private static final String ENTITY_NAME = "jobType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobTypeService jobTypeService;

    public JobTypeResource(JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }

    /**
     * {@code POST  /job-types} : Create a new jobType.
     *
     * @param jobType the jobType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobType, or with status {@code 400 (Bad Request)} if the jobType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-types")
    public ResponseEntity<JobType> createJobType(@Valid @RequestBody JobType jobType) throws URISyntaxException {
        log.debug("REST request to save JobType : {}", jobType);
        if (jobType.getId() != null) {
            throw new BadRequestAlertException("A new jobType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobType result = jobTypeService.save(jobType);
        return ResponseEntity.created(new URI("/api/job-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-types} : Updates an existing jobType.
     *
     * @param jobType the jobType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobType,
     * or with status {@code 400 (Bad Request)} if the jobType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-types")
    public ResponseEntity<JobType> updateJobType(@Valid @RequestBody JobType jobType) throws URISyntaxException {
        log.debug("REST request to update JobType : {}", jobType);
        if (jobType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobType result = jobTypeService.save(jobType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-types} : get all the jobTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobTypes in body.
     */
    @GetMapping("/job-types")
    public List<JobType> getAllJobTypes() {
        log.debug("REST request to get all JobTypes");
        return jobTypeService.findAll();
    }

    /**
     * {@code GET  /job-types/:id} : get the "id" jobType.
     *
     * @param id the id of the jobType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-types/{id}")
    public ResponseEntity<JobType> getJobType(@PathVariable Long id) {
        log.debug("REST request to get JobType : {}", id);
        Optional<JobType> jobType = jobTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobType);
    }

    /**
     * {@code DELETE  /job-types/:id} : delete the "id" jobType.
     *
     * @param id the id of the jobType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-types/{id}")
    public ResponseEntity<Void> deleteJobType(@PathVariable Long id) {
        log.debug("REST request to delete JobType : {}", id);
        jobTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
