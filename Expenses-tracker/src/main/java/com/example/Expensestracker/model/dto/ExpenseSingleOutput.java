package com.example.Expensestracker.model.dto;

import com.example.Expensestracker.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseSingleOutput {
    private boolean expenseStatus;
    private String expenseStatusMessage;
    private Expense expenseSingle;
}
