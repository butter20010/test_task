package com.example.testTask.service;

import com.example.testTask.api.dto.LoginRequestDto;
import com.example.testTask.api.exception.RecordNotFoundException;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.EmailRepository;
import com.example.testTask.dao.repo.PhoneRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final PhoneService phoneService;
  private final EmailService emailService;

  private final JwtUtil jwtUtil;

  public AuthService(PhoneService phoneService, EmailService emailService, JwtUtil jwtUtil) {
    this.phoneService = phoneService;
    this.emailService = emailService;
    this.jwtUtil = jwtUtil;
  }

  public String authenticateAndGenerateToken(LoginRequestDto request) {
    User user = findUser(request);
    if (!user.getPassword().equals(request.getPassword())) {
      throw new RuntimeException("Invalid password");
    }
    return jwtUtil.generateToken(user.getId());
  }

  private User findUser(LoginRequestDto request) {
    if (request.getEmail() != null) {
      return emailService.findByEmail(request.getEmail()).getUser();
    } else if (request.getPhone() != null) {
      return phoneService.findByPhone(request.getPhone()).getUser();
    } else {
      throw new RecordNotFoundException("Email or phone required");
    }
  }
}
