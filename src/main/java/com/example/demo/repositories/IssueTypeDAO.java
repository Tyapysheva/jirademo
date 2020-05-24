package com.example.demo.repositories;

import com.example.demo.model.IssueTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueTypeDAO extends CrudRepository<IssueTypeEntity, Long> {
}
