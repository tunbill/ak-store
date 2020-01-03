package com.ak.repository;
import com.ak.domain.ItemGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemGroup entity.
 */
@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup, Long> {
	Page<ItemGroup> findByCompanyId(Long companyId, Pageable pageable);
}
