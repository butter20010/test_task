package com.example.testTask.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "EmailDto", description = "Dto for creating or updating EmailData")
public class EmailDto {

  @NotNull
  private String email;
}
