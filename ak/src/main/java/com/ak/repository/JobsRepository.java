package com.ak.repository;
import com.ak.domain.Jobs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Jobs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {

}
