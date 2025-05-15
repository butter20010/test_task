package com.example.testTask.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @Setter(value = AccessLevel.NONE)
  private Long id;

  @JoinColumn(name = "USER_ID", unique = true, nullable = false)
  @OneToOne
  private User user;

  @Column(nullable = false)
  @Min(0)
  private BigDecimal balance;

  @Column(nullable = false)
  private BigDecimal initialBalance;
}
