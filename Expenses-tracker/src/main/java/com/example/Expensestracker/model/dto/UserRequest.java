package com.example.Expensestracker.model.dto;

import com.example.Expensestracker.model.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    private String userName;
    @Enumerated(EnumType.STRING)
    private Gender userGender;
    @NotBlank
    private String userAddress;
    @Email
    @Column(unique = true)
    private String userEmail;
    @NotBlank
    private String userPassword;
    @NotBlank
    private String phoneNumber;
}
