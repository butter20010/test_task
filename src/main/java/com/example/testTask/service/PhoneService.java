package com.example.testTask.service;

import com.example.testTask.api.exception.RecordNotFoundException;
import com.example.testTask.dao.model.PhoneData;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.PhoneRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

  private final PhoneRepository phoneRepo;


  public PhoneService(PhoneRepository phoneRepo) {
    this.phoneRepo = phoneRepo;
  }

  public void addPhone(String phone, User user) {

    PhoneData phoneData = PhoneData.builder().user(user).phone(phone).build();

    //will be PersistenceConflictException if phone number already taken
    //TODO add own exception
    phoneRepo.save(phoneData);
  }

  @CacheEvict(value = "phoneDataByPhone", key = "#phone")
  public void updatePhone(Long id, String phone, User user) {

    PhoneData phoneData = findPhoneDataById(id);
    if (!phoneData.getUser().getId().equals(user.getId())) {
      throw new RuntimeException("User can change only own phone");
    }
    phoneData.setPhone(phone);

    //will be PersistenceConflictException if phone number already taken
    //TODO add own exception
    phoneRepo.save(phoneData);
  }

  public void deletePhone(Long id, User user) {

    PhoneData phoneData = findPhoneDataById(id);
    if (!phoneData.getUser().getId().equals(user.getId())) {
      throw new RuntimeException("User can change only own phone");
    }
    if (user.getPhoneDataList().size() == 1) {
      throw new RuntimeException("Can't delete last phone for user");
    }
    phoneRepo.delete(phoneData);
  }

  @Cacheable(value = "phoneDataByPhone", key = "#phone")
  public PhoneData findByPhone(String phone) {
    return phoneRepo.findByPhone(phone)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Phone %s is not found", phone)));
  }

  private PhoneData findPhoneDataById(Long id) {
    return phoneRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format("Phone %s is not found", id)));
  }
}
