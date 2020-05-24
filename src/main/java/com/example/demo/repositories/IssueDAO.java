package com.example.demo.repositories;

import com.example.demo.model.IssueEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueDAO extends CrudRepository<IssueEntity, Long> {
}
