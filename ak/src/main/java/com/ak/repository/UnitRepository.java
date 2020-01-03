package com.ak.repository;
import com.ak.domain.Department;
import com.ak.domain.Unit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Unit entity.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
	Page<Department> findByCompanyId(Long companyId, Pageable pageable);
}
