package com.example.Expensestracker.service;

import com.example.Expensestracker.model.AuthenticationToken;
import com.example.Expensestracker.model.User;
import com.example.Expensestracker.model.dto.*;
import com.example.Expensestracker.repository.IUserRepo;
import com.example.Expensestracker.service.utility.EmailService;
import com.example.Expensestracker.service.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    EmailService emailService;

    public SignUpOutput signUpUser(UserRequest userRequest) throws NoSuchAlgorithmException {
        if (iUserRepo.existsByUserEmail(userRequest.getUserEmail())) {

            return SignUpOutput.builder()
                    .signUpStatus(false)
                    .signUpStatusMessage("Account Already Exist")
                    .build();
        }
        try {
            String password = PasswordEncrypter.encryptPassword(userRequest.getUserPassword());

             User newUser = User.builder()
                    .userName(userRequest.getUserName())
                    .userGender(userRequest.getUserGender())
                    .userAddress(userRequest.getUserAddress())
                    .userEmail(userRequest.getUserEmail())
                    .userPassword(password)
                    .phoneNumber(userRequest.getPhoneNumber())
                    .build();

            User savedUser = iUserRepo.save(newUser);

            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(savedUser.getUserEmail())
                    .subject("ACCOUNT CREATION")
                    .messageBody("Congratulations! Your Account Has been Successfully Created.\nYour Account Details: \n" +
                            "Account Name: " + savedUser.getUserName())
                    .build();
            emailService.sendEmailAlert(emailDetails);

            return SignUpOutput.builder()
                    .signUpStatus(true)
                    .signUpStatusMessage("Account Created Successfully")
                    .build();

        } catch (Exception e) {

            return SignUpOutput.builder()
                    .signUpStatus(false)
                    .signUpStatusMessage("Internal error occurred during sign up")
                    .build();

        }
    }

    public SignInOutput signInUser(SignInInput signInInput) {
        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {
            return SignInOutput.builder()
                    .signInStatus(false)
                    .signInStatusMessage("Email Does not exist")
                    .build();


        }

        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);

        if (existingUser == null) {
            return SignInOutput.builder()
                    .signInStatus(false)
                    .signInStatusMessage("Account Does not exist")
                    .build();
        }

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingUser.getUserPassword().equals(encryptedPassword)) {

                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailDetails emailDetails = EmailDetails.builder()
                        .recipient(existingUser.getUserEmail())
                        .subject("Auth Token")
                        .messageBody("Congratulations! You are Successfully SignIn .\nYour Account Details: \n" +
                                "Account Name: " + existingUser.getUserName()
                                + "\n Authentication token : \n" + authToken.getTokenValue())
                        .build();
                emailService.sendEmailAlert(emailDetails);

                return SignInOutput.builder()
                        .signInStatus(true)
                        .signInStatusMessage("Auth token sent on Mail")
                        .build();


            }
            else {
                return SignInOutput.builder()
                        .signInStatus(false)
                        .signInStatusMessage("Invalid credentials!!!")
                        .build();
            }
        }
        catch (Exception e) {

            return SignInOutput.builder()
                    .signInStatus(false)
                    .signInStatusMessage("Internal error occurred during sign in")
                    .build();

        }
    }

    public String sigOutUser(String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";

    }
}
