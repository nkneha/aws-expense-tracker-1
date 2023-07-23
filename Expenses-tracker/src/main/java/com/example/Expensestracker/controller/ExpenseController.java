package com.example.Expensestracker.controller;

import com.example.Expensestracker.model.dto.ExpenseMultiOutput;
import com.example.Expensestracker.model.dto.ExpenseRequest;
import com.example.Expensestracker.model.dto.ExpenseSingleOutput;
import com.example.Expensestracker.service.AuthenticationService;
import com.example.Expensestracker.service.ExpenseService;
import com.example.Expensestracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping("expense/create")
    public ExpenseSingleOutput createExpense (@RequestBody ExpenseRequest expenseRequest ,
                                              @RequestParam String email ,
                                              @RequestParam String token)  {

        if (authenticationService.authenticate(email, token)) {

            return expenseService.createExpense(email,expenseRequest);

        } else {
            return ExpenseSingleOutput.builder()
                    .expenseStatus(false)
                    .expenseStatusMessage("Sign out not allowed for non authenticated user.")
                    .expenseSingle(null)
                    .build();
        }
    }

    @GetMapping("expenses")
    public ExpenseMultiOutput generateExpense(@RequestParam String email ,
                                              @RequestParam String token,
                                              @RequestParam String startDate ,
                                              @RequestParam String endDate) {

        if (authenticationService.authenticate(email, token)) {

            return expenseService.generateExpense(email, startDate, endDate);

        } else {
            return ExpenseMultiOutput.builder()
                    .expenseStatus(false)
                    .expenseStatusMessage("Sign out not allowed for non authenticated user.")
                    .expenseReport(null)
                    .build();
        }
    }
    @GetMapping("monthly")
    public ExpenseMultiOutput generateMonthlyExpense(@RequestParam String email ,
                                                     @RequestParam String token,
                                                     @RequestParam String month){

        if (authenticationService.authenticate(email, token)) {

            return expenseService.generateMonthlyExpense(email,month);

        } else {
            return ExpenseMultiOutput.builder()
                    .expenseStatus(false)
                    .expenseStatusMessage("Sign out not allowed for non authenticated user.")
                    .expenseReport(null)
                    .build();
        }

    }




}
