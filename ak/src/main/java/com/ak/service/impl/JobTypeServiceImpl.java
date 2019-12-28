package com.ak.service.impl;

import com.ak.service.JobTypeService;
import com.ak.domain.JobType;
import com.ak.repository.JobTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link JobType}.
 */
@Service
@Transactional
public class JobTypeServiceImpl implements JobTypeService {

    private final Logger log = LoggerFactory.getLogger(JobTypeServiceImpl.class);

    private final JobTypeRepository jobTypeRepository;

    public JobTypeServiceImpl(JobTypeRepository jobTypeRepository) {
        this.jobTypeRepository = jobTypeRepository;
    }

    /**
     * Save a jobType.
     *
     * @param jobType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JobType save(JobType jobType) {
        log.debug("Request to save JobType : {}", jobType);
        return jobTypeRepository.save(jobType);
    }

    /**
     * Get all the jobTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobType> findAll() {
        log.debug("Request to get all JobTypes");
        return jobTypeRepository.findAll();
    }


    /**
     * Get one jobType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JobType> findOne(Long id) {
        log.debug("Request to get JobType : {}", id);
        return jobTypeRepository.findById(id);
    }

    /**
     * Delete the jobType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobType : {}", id);
        jobTypeRepository.deleteById(id);
    }
}
