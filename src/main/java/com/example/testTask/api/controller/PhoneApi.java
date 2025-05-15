package com.example.testTask.api.controller;

import com.example.testTask.api.dto.PhoneDto;
import com.example.testTask.service.PhoneService;
import com.example.testTask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone")
@Tag(name = "phone", description = "Api for PhoneData entity")
public class PhoneApi {

  private final PhoneService phoneService;

  private final UserService userService;

  public PhoneApi(PhoneService phoneService, UserService userService) {
    this.phoneService = phoneService;
    this.userService = userService;
  }

  @PostMapping
  @Operation(summary = "Create new phone for user")
  public void addPhone(@Valid @RequestBody PhoneDto phoneDto, @RequestHeader @NotNull String authorization) {

    phoneService.addPhone(phoneDto.getPhone(), userService.findUserByToken(authorization));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update existing phone for user")
  public void updatePhone(@PathVariable("id") Long id, @Valid @RequestBody PhoneDto phoneDto, @RequestHeader @NotNull String authorization) {

    phoneService.updatePhone(id, phoneDto.getPhone(), userService.findUserByToken(authorization));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete phone for user")
  public void deletePhone(@PathVariable("id") Long id, @RequestHeader @NotNull String authorization) {

    phoneService.deletePhone(id, userService.findUserByToken(authorization));
  }
}
