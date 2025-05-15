package com.example.testTask.api.controller;

import com.example.testTask.api.dto.EmailDto;
import com.example.testTask.service.EmailService;
import com.example.testTask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@Tag(name = "email", description = "Api for EmailData entity")
public class EmailApi {

  private final EmailService emailService;

  private final UserService userService;

  public EmailApi(EmailService emailService, UserService userService) {
    this.emailService = emailService;
    this.userService = userService;
  }

  @PostMapping
  @Operation(summary = "Create new email for user")
  public void addEmail(@Valid @RequestBody EmailDto emailDto, @RequestHeader @NotNull String authorization) {

    emailService.addEmail(emailDto.getEmail(), userService.findUserByToken(authorization));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update existing email for user")
  public void updateEmail(@PathVariable("id") Long id, @Valid @RequestBody EmailDto emailDto, @RequestHeader @NotNull String authorization) {

    emailService.updateEmail(id, emailDto.getEmail(), userService.findUserByToken(authorization));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete email for user")
  public void deleteEmail(@PathVariable("id") Long id, @RequestHeader @NotNull String authorization) {

    emailService.deleteEmail(id, userService.findUserByToken(authorization));
  }
}
