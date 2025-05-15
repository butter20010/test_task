package com.example.testTask.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneData implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Setter(value = AccessLevel.NONE)
  private Long id;

  @JoinColumn(name = "USER_ID")
  @ManyToOne
  private User user;

  @Column(unique = true)
  @Pattern(regexp = "^[78]\\d{10}$")
  private String phone;
}
