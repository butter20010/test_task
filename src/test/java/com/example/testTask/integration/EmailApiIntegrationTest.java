package com.example.testTask.integration;

import com.example.testTask.api.dto.EmailDto;
import com.example.testTask.dao.model.EmailData;
import com.example.testTask.dao.model.User;
import com.example.testTask.dao.repo.EmailRepository;
import com.example.testTask.dao.repo.UserRepository;
import com.example.testTask.service.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class EmailApiIntegrationTest {

  private final Logger log = LoggerFactory.getLogger(EmailApiIntegrationTest.class);

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private EmailRepository emailRepo;

  @Autowired
  private JwtUtil jwtUtil;

  private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
          "postgres:16-alpine"
  );

  private User user;

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @BeforeEach
  void setup(TestInfo info) {
    log.info("Starting test {}", info.getDisplayName());
    user = userRepo.save(User.builder().name("name").password("12345678").build());
  }

  @AfterEach
  void after(TestInfo info) {
    log.info("Finishing test {}", info.getDisplayName());
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Test
  void addEmail_ok() throws Exception {
    String token = "Bearer " + jwtUtil.generateToken(user.getId());
    EmailDto dto = new EmailDto();
    dto.setEmail("abcd1234@ab.com");

    mockMvc.perform(post("/email")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isOk());

    Optional<EmailData> emailDataOptional = emailRepo.findByEmail(dto.getEmail());
    assertTrue(emailDataOptional.isPresent());
    assertEquals(dto.getEmail(), emailDataOptional.get().getEmail());
  }

  @Test
  void deleteEmail_ok() throws Exception {
    String token = "Bearer " + jwtUtil.generateToken(user.getId());
    EmailData emailData = EmailData.builder().email("1111@abcd.com").user(user).build();
    emailRepo.save(emailData);
    EmailData emailData2 = EmailData.builder().email("2222@abcd.com").user(user).build();
    emailRepo.save(emailData2);
    Optional<EmailData> emailDataOptional = emailRepo.findById(emailData2.getId());
    assertTrue(emailDataOptional.isPresent());

    mockMvc.perform(delete("/email/" + emailData2.getId())
                    .header("Authorization", token))
            .andExpect(status().isOk());

    emailDataOptional = emailRepo.findById(emailData2.getId());
    assertTrue(emailDataOptional.isEmpty());
  }
}
