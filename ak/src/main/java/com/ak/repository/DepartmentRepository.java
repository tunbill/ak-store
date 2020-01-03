package com.ak.repository;
import com.ak.domain.Department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Department entity.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	 Page<Department> findByCompanyId(Long companyId, Pageable pageable);
}
