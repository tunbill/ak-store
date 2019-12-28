package com.ak.repository;
import com.ak.domain.Industry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Industry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {

}
