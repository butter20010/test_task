package com.example.testTask.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "PhoneDto", description = "Dto for creating or updating PhoneData")
public class PhoneDto {

  @Pattern(regexp = "^[78]\\d{10}$", message = "Phone should be in 70000000000 format")
  @NotNull
  private String phone;
}
