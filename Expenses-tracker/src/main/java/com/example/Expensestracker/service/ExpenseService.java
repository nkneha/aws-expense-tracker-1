package com.example.Expensestracker.service;

import com.example.Expensestracker.model.Expense;
import com.example.Expensestracker.model.User;
import com.example.Expensestracker.model.dto.EmailDetails;
import com.example.Expensestracker.model.dto.ExpenseMultiOutput;
import com.example.Expensestracker.model.dto.ExpenseRequest;
import com.example.Expensestracker.model.dto.ExpenseSingleOutput;
import com.example.Expensestracker.repository.IExpenseRepo;
import com.example.Expensestracker.repository.IUserRepo;
import com.example.Expensestracker.service.utility.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    IExpenseRepo iExpenseRepo;

    @Autowired
    IUserRepo iUserRepo;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;
    public ExpenseSingleOutput createExpense(String email, ExpenseRequest expenseRequest) {
        User existingUser = iUserRepo.findFirstByUserEmail(email);

        Expense newExpense = Expense.builder()
                .title(expenseRequest.getExpenseTitle())
                .description(expenseRequest.getExpenseDescription())
                .price(expenseRequest.getExpensePrice())
                .user(existingUser)
                .build();

        Expense savedExpense = iExpenseRepo.save(newExpense);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedExpense.getUser().getUserEmail())
                .subject("Expense Update")
                .messageBody("Congratulations! Your Expense Has been Successfully Created.\nYour Account Details: \n" +
                        "Account Name: " + savedExpense.getUser().getUserName() + " "
                        + "\n" + savedExpense )
                .build();

        emailService.sendEmailAlert(emailDetails);

        return ExpenseSingleOutput.builder()

                .expenseStatus(true)
                .expenseStatusMessage("Your Expense Added Successfully also sent over Mail")
                .expenseSingle(savedExpense)
                .build();
    }

    public ExpenseMultiOutput generateExpense(String email, String startDate, String endDate) {
        LocalDate start =LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end =LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Expense> expenseList= iExpenseRepo.findAll().stream()
                .filter(expense ->expense.getUser().getUserEmail().equals(email))
                .filter(expense -> expense.getBuyDate().isEqual(start))
                .filter(expense -> expense.getBuyDate().isEqual(end)).toList();

        User existingUser = iUserRepo.findFirstByUserEmail(email);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(existingUser.getUserEmail())
                .subject("Expense Report")
                .messageBody("Your Account Details: \n" +
                        "Account Name: " + existingUser.getUserName() + " "
                        + "\n Your Expense Report from " +startDate + " to " +endDate  +"\n"+ expenseList )
                .build();
        emailService.sendEmailAlert(emailDetails);

        return ExpenseMultiOutput.builder()
                .expenseStatus(true)
                .expenseStatusMessage("Your Expense Report is sent Over Mail")
                .expenseReport(expenseList)
                .build();

    }

    public ExpenseMultiOutput generateMonthlyExpense(String email, String month) {
        List<Expense> expenseList = iExpenseRepo.findAll().stream()
                .filter(expense ->expense.getUser().getUserEmail().equals(email))
                .filter(expense -> expense.getBuyDate().toString().substring(5,7).equals(month))
                .toList();

        User existingUser = iUserRepo.findFirstByUserEmail(email);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(existingUser.getUserEmail())
                .subject("Expense Report")
                .messageBody("Your Account Details: \n" +
                        "Account Name: " + existingUser.getUserName() + " "
                        + "\n Your Expense Report for month: " + month +"\n"+ expenseList )
                .build();
        emailService.sendEmailAlert(emailDetails);

        return ExpenseMultiOutput.builder()

                .expenseStatus(true)
                .expenseStatusMessage("Your Monthly Expense Report is sent Over Mail")
                .expenseReport(expenseList)

                .build();






    }
}
