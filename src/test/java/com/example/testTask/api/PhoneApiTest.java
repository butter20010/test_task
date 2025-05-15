package com.example.testTask.api;

import com.example.testTask.api.controller.PhoneApi;
import com.example.testTask.api.dto.PhoneDto;
import com.example.testTask.service.PhoneService;
import com.example.testTask.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneApi.class)
public class PhoneApiTest {

  private final Logger log = LoggerFactory.getLogger(PhoneApiTest.class);

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockitoBean
  private PhoneService phoneService;

  @MockitoBean
  private UserService userService;

  @BeforeEach
  void before(TestInfo info) {
    log.info("Starting test {}", info.getDisplayName());
  }

  @AfterEach
  void after(TestInfo info) {
    log.info("Finishing test {}", info.getDisplayName());
  }

  @Test
  public void addNewPhone_ok(TestInfo info) throws Exception {
    PhoneDto dto = new PhoneDto();
    dto.setPhone("77777777777");
    mockMvc.perform(post("/phone").header("Authorization", "authorization").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto))).andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());
    log.info("Finishing test {}", info.getDisplayName());
  }
}
