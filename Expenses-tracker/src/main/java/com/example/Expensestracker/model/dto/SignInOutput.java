package com.example.Expensestracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class SignInOutput {
    private boolean signInStatus;
    private String signInStatusMessage;
}
