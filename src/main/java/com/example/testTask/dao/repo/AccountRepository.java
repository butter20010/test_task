package com.example.testTask.dao.repo;

import com.example.testTask.dao.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

  @Query("SELECT a FROM Account a WHERE a.balance < a.initialBalance * 2.07")
  List<Account> findAllByBalanceToChange();

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Account> findByUserId(Long userId);
}
