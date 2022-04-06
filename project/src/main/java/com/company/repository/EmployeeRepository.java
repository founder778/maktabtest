package com.company.repository;

import com.company.entity.EmployeeEntity;
import com.company.enums.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Integer> {
    @Query("SELECT e FROM EmployeeEntity e WHERE e.role=?1 AND e.status='ACTIVE' ORDER BY e.createdDate desc")
    List<EmployeeEntity> filterByRole( EmployeeRole role);
}
