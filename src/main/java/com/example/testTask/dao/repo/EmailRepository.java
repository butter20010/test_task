package com.example.testTask.dao.repo;

import com.example.testTask.dao.model.EmailData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailRepository extends CrudRepository<EmailData, Long> {

  Optional<EmailData> findByEmail(String email);
}
