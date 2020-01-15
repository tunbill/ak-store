package com.ak.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ak.domain.CustomerType;


/**
 * Spring Data  repository for the CustomerType entity.
 */
@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long>, JpaSpecificationExecutor<CustomerType> {
	Page<CustomerType> findByCompanyId(Long companyId, Pageable pageable);
}
