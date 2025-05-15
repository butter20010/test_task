package com.example.testTask.dao.repo;

import com.example.testTask.dao.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UserRepository extends CrudRepository<User, Long> {

  @Query("""
              SELECT u FROM User u
              LEFT JOIN PhoneData p ON p.user.id = u.id
              LEFT JOIN EmailData e ON e.user.id = u.id
              WHERE (:dateOfBirth IS NULL OR u.dateOfBirth > :dateOfBirth)
                AND (:phone IS NULL OR p.phone = :phone)
                AND (:email IS NULL OR e.email = :email)
                AND (:name IS NULL OR u.name LIKE CONCAT(cast(:name as string), '%'))
          """)
  Page<User> searchUsers(
          @Param("dateOfBirth") LocalDate dateOfBirth,
          @Param("phone") String phone,
          @Param("email") String email,
          @Param("name") String name,
          Pageable pageable
  );
}
