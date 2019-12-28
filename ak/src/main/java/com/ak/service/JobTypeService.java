package com.ak.service;

import com.ak.domain.JobType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link JobType}.
 */
public interface JobTypeService {

    /**
     * Save a jobType.
     *
     * @param jobType the entity to save.
     * @return the persisted entity.
     */
    JobType save(JobType jobType);

    /**
     * Get all the jobTypes.
     *
     * @return the list of entities.
     */
    List<JobType> findAll();


    /**
     * Get the "id" jobType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobType> findOne(Long id);

    /**
     * Delete the "id" jobType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
