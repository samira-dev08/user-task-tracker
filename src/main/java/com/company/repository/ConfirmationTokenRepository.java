package com.company.repository;

import com.company.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Integer> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

}
