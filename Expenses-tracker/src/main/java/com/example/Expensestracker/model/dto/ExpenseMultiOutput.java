package com.example.Expensestracker.model.dto;

import com.example.Expensestracker.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseMultiOutput {
    private boolean expenseStatus;
    private String expenseStatusMessage;
    private List<Expense> expenseReport;

}
