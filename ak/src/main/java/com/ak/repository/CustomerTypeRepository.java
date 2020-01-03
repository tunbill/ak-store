package com.ak.repository;
import com.ak.domain.CustomerType;
import com.ak.domain.Department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerType entity.
 */
@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long>, JpaSpecificationExecutor<CustomerType> {
	Page<CustomerType> findByCompanyId(Long companyId, Pageable pageable);
}
