package com.company.repository;

import com.company.entity.PasswordResetToken;
import com.company.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken,Integer> {
    Optional<PasswordResetToken> findByToken(String token);
}
