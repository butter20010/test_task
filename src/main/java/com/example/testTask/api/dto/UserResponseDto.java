package com.example.testTask.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(name = "UserResponseDto", description = "Dto with information about User")
public class UserResponseDto {

  private Long id;
  private String name;
  private LocalDate dateOfBirth;

}
