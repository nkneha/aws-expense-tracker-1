package com.example.Expensestracker.repository;

import com.example.Expensestracker.model.AuthenticationToken;
import com.example.Expensestracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
