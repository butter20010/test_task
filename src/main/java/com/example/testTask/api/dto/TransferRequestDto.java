package com.example.testTask.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "TransferRequestDto", description = "Dto with necessary data for transfer")
public class TransferRequestDto {

  @NotNull
  private Long toUserId;

  @NotNull
  @DecimalMin(value = "0.01")
  private BigDecimal value;
}
