package com.example.testTask.api.controller;

import com.example.testTask.api.dto.TransferRequestDto;
import com.example.testTask.service.AccountService;
import com.example.testTask.service.JwtUtil;
import com.example.testTask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "account", description = "Api for Account entity")
public class AccountApi {

  private final JwtUtil jwtUtil;
  private final AccountService accountService;

  public AccountApi(JwtUtil jwtUtil, AccountService accountService) {
    this.jwtUtil = jwtUtil;
    this.accountService = accountService;
  }

  @PostMapping("/transfer")
  @Operation(summary = "Transfer money from balance from one user to another")
  public void transferMoney(@RequestHeader @NotNull String authorization, @RequestBody @Valid TransferRequestDto transferDto) {
    accountService.transfer(jwtUtil.extractUserId(authorization), transferDto.getToUserId(), transferDto.getValue());
  }
}
