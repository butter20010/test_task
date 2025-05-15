package com.example.testTask.service;

import com.example.testTask.api.exception.RecordNotFoundException;
import com.example.testTask.dao.model.Account;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  private final Logger log = LoggerFactory.getLogger(AccountServiceTest.class);

  @Mock
  private AccountRepository accountRepo;

  @InjectMocks
  private AccountService accountService;

  private Account fromAccount;
  private Account toAccount;

  private static final Long FROM_USER_ID = 1L;
  private static final Long TO_USER_ID = 2L;

  @BeforeEach
  void setUp(TestInfo info) {
    log.info("Starting test {}", info.getDisplayName());
    User fromUser = User.builder().id(FROM_USER_ID).name("name1").build();
    fromAccount = Account.builder().user(fromUser).balance(BigDecimal.valueOf(100)).build();

    User toUser = User.builder().id(TO_USER_ID).name("name2").build();
    toAccount = Account.builder().user(toUser).balance(BigDecimal.valueOf(50)).build();
  }

  @AfterEach
  void after(TestInfo info) {
    log.info("Finishing test {}", info.getDisplayName());
  }

  @Test
  void transfer_ok() {

    Mockito.when(accountRepo.findByUserId(FROM_USER_ID)).thenReturn(Optional.of(fromAccount));
    Mockito.when(accountRepo.findByUserId(TO_USER_ID)).thenReturn(Optional.of(toAccount));

    accountService.transfer(FROM_USER_ID, TO_USER_ID, BigDecimal.valueOf(30));

    assertEquals(BigDecimal.valueOf(70), fromAccount.getBalance());
    assertEquals(BigDecimal.valueOf(80), toAccount.getBalance());

    Mockito.verify(accountRepo).saveAll(List.of(fromAccount, toAccount));
  }

  @Test
  void transfer_not_enough() {

    Mockito.when(accountRepo.findByUserId(FROM_USER_ID)).thenReturn(Optional.of(fromAccount));
    Mockito.when(accountRepo.findByUserId(TO_USER_ID)).thenReturn(Optional.of(toAccount));

    RuntimeException ex = assertThrows(RuntimeException.class, () ->
            accountService.transfer(FROM_USER_ID, TO_USER_ID, BigDecimal.valueOf(150))
    );

    assertEquals("Can't complete operation. Not enough money", ex.getMessage());
    Mockito.verify(accountRepo, Mockito.never()).saveAll(Mockito.any());
  }

  @Test
  void transfer_negative_value() {

    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            accountService.transfer(FROM_USER_ID, TO_USER_ID, BigDecimal.valueOf(-1))
    );

    assertEquals("Value must be positive", ex.getMessage());
    Mockito.verify(accountRepo, Mockito.never()).saveAll(Mockito.any());
  }

  @Test
  void transfer_to_self() {

    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            accountService.transfer(FROM_USER_ID, FROM_USER_ID, BigDecimal.valueOf(150))
    );

    assertEquals("Cannot transfer to self", ex.getMessage());
    Mockito.verify(accountRepo, Mockito.never()).saveAll(Mockito.any());
  }

  @Test
  void transfer_sender_not_found() {

    RecordNotFoundException ex = assertThrows(RecordNotFoundException.class, () ->
            accountService.transfer(FROM_USER_ID, TO_USER_ID, BigDecimal.valueOf(150))
    );

    assertEquals("Sender not found", ex.getMessage());
    Mockito.verify(accountRepo, Mockito.never()).saveAll(Mockito.any());
  }

  @Test
  void transfer_receiver_not_found() {

    Mockito.when(accountRepo.findByUserId(FROM_USER_ID)).thenReturn(Optional.of(fromAccount));
    RecordNotFoundException ex = assertThrows(RecordNotFoundException.class, () ->
            accountService.transfer(FROM_USER_ID, TO_USER_ID, BigDecimal.valueOf(150))
    );

    assertEquals("Receiver not found", ex.getMessage());
    Mockito.verify(accountRepo, Mockito.never()).saveAll(Mockito.any());
  }
}
