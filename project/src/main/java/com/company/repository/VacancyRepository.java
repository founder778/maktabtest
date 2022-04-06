package com.company.repository;

import com.company.entity.VacancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<VacancyEntity,Integer> {

    boolean existsById(Integer id);
}
