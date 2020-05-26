package com.example.demo.repositories;

import com.example.demo.model.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDAO extends CrudRepository<ProjectEntity, Long> {
}
