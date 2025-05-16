package com.example.testTask.service;

import com.example.testTask.api.exception.RecordNotFoundException;
import com.example.testTask.dao.model.Account;
import com.example.testTask.dao.repo.AccountRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class AccountService {

  private static final Logger log = LoggerFactory.getLogger(AccountService.class);

  private final AccountRepository accountRepo;

  public AccountService(AccountRepository accountRepo) {
    this.accountRepo = accountRepo;
  }

  //in case if we will use this method somewhere else (not only for transfer)
  //@Cacheable(value = "accountByUserId", key = "#id")
  public Account findByUserId(Long id) {
    return accountRepo.findByUserId(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Account for user %s not found", id)));
  }

  @Transactional
  //@CacheEvict(value = "accountByUserId", key = "#id")
  public void transfer(Long fromUserId, Long toUserId, BigDecimal value) {

    log.info("Starting AccountService.transfer() from user {} to user {}", fromUserId, toUserId);
    if (value.compareTo(BigDecimal.ZERO) <= 0) {
      log.error("Value for transfer must be positive");
      throw new IllegalArgumentException("Value must be positive");
    }

    if (fromUserId.equals(toUserId)) {
      log.error("Cannot transfer to self");
      throw new IllegalArgumentException("Cannot transfer to self");
    }

    Account fromAccount = findByUserId(fromUserId);
    Account toAccount = findByUserId(toUserId);

    if (fromAccount.getBalance().compareTo(value) < 0) {
      log.error("Can't complete operation. Not enough money");
      throw new RuntimeException("Can't complete operation. Not enough money");
    }

    fromAccount.setBalance(fromAccount.getBalance().subtract(value));
    toAccount.setBalance(toAccount.getBalance().add(value));

    accountRepo.saveAll(List.of(fromAccount, toAccount));
    log.info("Finishing AccountService.transfer() from user {} to user {}", fromUserId, toUserId);
  }

}
