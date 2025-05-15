package com.example.testTask.api.controller;

import com.example.testTask.api.dto.LoginRequestDto;
import com.example.testTask.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authorization", description = "Api for authorization")
public class LoginApi {

  private final AuthService authService;

  public LoginApi(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  @Operation(summary = "Login to system")
  public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {

    String token = authService.authenticateAndGenerateToken(request);
    return ResponseEntity.ok(token);
  }

}
