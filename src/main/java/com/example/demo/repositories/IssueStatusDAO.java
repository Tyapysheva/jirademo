package com.example.demo.repositories;

import com.example.demo.model.IssueStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueStatusDAO extends CrudRepository<IssueStatusEntity, Long> {
}
