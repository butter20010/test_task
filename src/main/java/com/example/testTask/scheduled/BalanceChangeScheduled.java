package com.example.testTask.scheduled;

import com.example.testTask.dao.model.Account;
import com.example.testTask.dao.repo.AccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BalanceChangeScheduled {

  private final AccountRepository accountRepo;

  public BalanceChangeScheduled(AccountRepository accountRepo) {
    this.accountRepo = accountRepo;
  }

  @Scheduled(fixedRate = 30000)
  public void changeBalance() {

    List<Account> accounts = accountRepo.findAllByBalanceToChange();
    accounts.forEach(account -> {
      BigDecimal newBalance = account.getBalance().multiply(BigDecimal.valueOf(1.1));
      BigDecimal maxBalance = account.getInitialBalance().multiply(BigDecimal.valueOf(2.07));
      if (newBalance.compareTo(maxBalance) > 0) {
        newBalance = maxBalance;
      }
      account.setBalance(newBalance);
    });
    accountRepo.saveAll(accounts);
  }
}
