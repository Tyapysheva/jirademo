package com.example.demo.repositories;


import com.example.demo.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<UserEntity, String> {
}
