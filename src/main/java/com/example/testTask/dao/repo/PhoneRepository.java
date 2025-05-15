package com.example.testTask.dao.repo;

import com.example.testTask.dao.model.PhoneData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhoneRepository extends CrudRepository<PhoneData, Long> {

  Optional<PhoneData> findByPhone(String phone);
}
