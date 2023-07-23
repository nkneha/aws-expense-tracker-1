package com.example.Expensestracker.repository;

import com.example.Expensestracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User,Long> {
    boolean existsByUserEmail(String userEmail);

    User findFirstByUserEmail(String signInEmail);
}
