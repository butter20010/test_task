package com.example.testTask.dao;

import com.example.testTask.dao.model.Account;
import com.example.testTask.dao.model.EmailData;
import com.example.testTask.dao.model.PhoneData;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.AccountRepository;
import com.example.testTask.dao.repo.EmailRepository;
import com.example.testTask.dao.repo.PhoneRepository;
import com.example.testTask.dao.repo.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class Filler {

  private final UserRepository userRepo;
  private final PhoneRepository phoneRepo;
  private final EmailRepository emailRepo;
  private final AccountRepository accountRepo;

  public Filler(UserRepository userRepo, PhoneRepository phoneRepo, EmailRepository emailRepo, AccountRepository accountRepo) {
    this.userRepo = userRepo;
    this.phoneRepo = phoneRepo;
    this.emailRepo = emailRepo;
    this.accountRepo = accountRepo;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void createUsers() {
    User user = User.builder().name("user1").dateOfBirth(LocalDate.now()).password("12345678").build();
    userRepo.save(user);

    Account account = Account.builder().balance(BigDecimal.valueOf(2)).initialBalance(BigDecimal.valueOf(2)).build();
    account.setUser(user);
    accountRepo.save(account);

    PhoneData phoneData = PhoneData.builder().phone("79260000000").user(user).build();
    phoneRepo.save(phoneData);

    EmailData emailData = EmailData.builder().email("abcd@email.com").user(user).build();
    emailRepo.save(emailData);

    User user2 = User.builder().name("user2").dateOfBirth(LocalDate.now()).password("12345678").build();
    userRepo.save(user2);

    Account account2 = Account.builder().balance(BigDecimal.valueOf(1)).initialBalance(BigDecimal.valueOf(1)).build();
    account2.setUser(user2);
    accountRepo.save(account2);

    PhoneData phoneData2 = PhoneData.builder().phone("72222222222").user(user2).build();
    phoneRepo.save(phoneData2);

    EmailData emailData2 = EmailData.builder().email("abcd2@email.com").user(user2).build();
    emailRepo.save(emailData2);

  }
}
