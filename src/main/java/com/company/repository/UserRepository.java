package com.company.repository;

import com.company.entity.User;
import com.company.enums.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<List<User>>findByEmailStatus(EmailStatus emailStatus);

    boolean existsByEmail(String email);

}
