package com.example.testTask.service;

import com.example.testTask.api.exception.RecordNotFoundException;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

  private final UserRepository userRepo;

  private final JwtUtil jwtUtil;

  public UserService(UserRepository userRepo, JwtUtil jwtUtil) {
    this.userRepo = userRepo;
    this.jwtUtil = jwtUtil;
  }

  public User findUserByToken(String token) {
    Long id = jwtUtil.extractUserId(token);
    return userRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format("User with id %s is not found", id)));
  }

  public Page<User> getUsersByFilter(Integer pageNumber, Integer pageSize, String name, String phone, String email, LocalDate dateOfBirth) {
    return userRepo.searchUsers(dateOfBirth, phone, email, name, PageRequest.of(pageNumber, pageSize));

  }
}
