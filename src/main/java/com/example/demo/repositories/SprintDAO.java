package com.example.demo.repositories;


import com.example.demo.model.SprintEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintDAO extends CrudRepository<SprintEntity, Long> {
}
