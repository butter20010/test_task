package com.example.testTask.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Setter(value = AccessLevel.NONE)
  private Long id;

  @Column
  private String name;

  @Column
  private LocalDate dateOfBirth;

  @Column(nullable = false)
  @Size(min = 8, max = 500)
  private String password;

  @OneToMany(mappedBy = "user")
  private List<PhoneData> phoneDataList;

  @OneToMany(mappedBy = "user")
  private List<EmailData> emailDataList;

}
