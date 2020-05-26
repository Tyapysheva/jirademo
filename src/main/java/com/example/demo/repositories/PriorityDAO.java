package com.example.demo.repositories;

import com.example.demo.model.PriorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityDAO extends CrudRepository<PriorityEntity, Long> {
}
