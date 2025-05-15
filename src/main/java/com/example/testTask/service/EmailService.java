package com.example.testTask.service;

import com.example.testTask.api.exception.RecordNotFoundException;
import com.example.testTask.dao.model.EmailData;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.EmailRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final EmailRepository emailRepo;

  public EmailService(EmailRepository emailRepo) {
    this.emailRepo = emailRepo;
  }

  public void addEmail(String email, User user) {

    EmailData emailData = EmailData.builder().user(user).email(email).build();

    //will be PersistenceConflictException if email is already taken
    //TODO add own exception
    emailRepo.save(emailData);
  }

  @CacheEvict(value = "emailDataByEmail", key = "#email")
  public void updateEmail(Long id, String email, User user) {

    EmailData emailData = findEmailDataById(id);
    if (!emailData.getUser().getId().equals(user.getId())) {
      throw new RuntimeException("User can change only own email");
    }
    emailData.setEmail(email);

    //will be PersistenceConflictException if email is already taken
    //TODO add own exception
    emailRepo.save(emailData);
  }

  public void deleteEmail(Long id, User user) {

    EmailData emailData = findEmailDataById(id);
    if (!emailData.getUser().getId().equals(user.getId())) {
      throw new RuntimeException("User can change only own email");
    }
    if (user.getEmailDataList().size() == 1) {
      throw new RuntimeException("Can't delete last email for user");
    }
    emailRepo.delete(emailData);
  }

  @Cacheable(value = "emailDataByEmail", key = "#email")
  public EmailData findByEmail(String email) {
    return emailRepo.findByEmail(email)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Email %s is not found", email)));
  }

  private EmailData findEmailDataById(Long id) {
    return emailRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format("Email %s is not found", id)));
  }
}
