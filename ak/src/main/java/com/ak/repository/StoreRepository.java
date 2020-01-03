package com.ak.repository;
import com.ak.domain.Store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Store entity.
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	 Page<Store> findByCompanyId(Long companyId, Pageable pageable);
}
