package com.example.testTask.api.controller;

import com.example.testTask.api.dto.UserResponseDto;
import com.example.testTask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "Api for User entity")
public class UserApi {

  private final UserService userService;

  private final ModelMapper mapper;

  public UserApi(UserService userService, ModelMapper mapper) {
    this.userService = userService;
    this.mapper = mapper;
  }

  @GetMapping("/list")
  @Operation(summary = "Get list of UserResponseDto by filters")
  public List<UserResponseDto> getUsersList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize,
                                            @RequestParam(required = false) String name, @RequestParam(required = false) String phone,
                                            @RequestParam(required = false) String email, @RequestParam(required = false) LocalDate dateOfBirth) {

    return userService.getUsersByFilter(pageNumber, pageSize, name, phone, email, dateOfBirth).getContent().stream().map(user -> mapper.map(user, UserResponseDto.class)).toList();
  }
}
