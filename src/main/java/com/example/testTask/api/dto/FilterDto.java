package com.example.testTask.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(name = "FilterDto", description = "Dto for filtering users")
public class FilterDto {

  private String phone;

  private String name;

  private String email;

  private LocalDate dateOfBirth;
}
