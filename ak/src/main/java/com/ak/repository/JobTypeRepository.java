package com.ak.repository;
import com.ak.domain.JobType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JobType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {

}
