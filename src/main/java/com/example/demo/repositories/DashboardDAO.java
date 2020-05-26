package com.example.demo.repositories;

import com.example.demo.model.DashboardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardDAO extends CrudRepository<DashboardEntity, Long> {
}
