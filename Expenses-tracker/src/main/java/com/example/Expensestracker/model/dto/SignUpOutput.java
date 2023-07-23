package com.example.Expensestracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SignUpOutput {
    private boolean signUpStatus;
    private String signUpStatusMessage;
}
