package com.example.Expensestracker.repository;

import com.example.Expensestracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepo extends JpaRepository<Expense,Long> {
}
