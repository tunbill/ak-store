package com.ak.repository;
import com.ak.domain.Terms;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Terms entity.
 */
@Repository
public interface TermsRepository extends JpaRepository<Terms, Long> {
}
