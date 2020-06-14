package com.example.demo.repositories;

import com.example.demo.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends CrudRepository<RoleEntity, Long> {
}
