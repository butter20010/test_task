package com.example.testTask.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailData implements Serializable {

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
  private String email;
}
