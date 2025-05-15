package com.example.testTask.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "LoginRequestDto", description = "Dto for login in system")
public class LoginRequestDto {

  private String email;
  private String phone;
  private String password;
}
