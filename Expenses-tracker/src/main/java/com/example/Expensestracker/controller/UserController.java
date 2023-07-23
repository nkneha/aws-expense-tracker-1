package com.example.Expensestracker.controller;

import com.example.Expensestracker.model.User;
import com.example.Expensestracker.model.dto.SignInInput;
import com.example.Expensestracker.model.dto.SignInOutput;
import com.example.Expensestracker.model.dto.SignUpOutput;
import com.example.Expensestracker.model.dto.UserRequest;
import com.example.Expensestracker.service.AuthenticationService;
import com.example.Expensestracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("user/signup")
    public SignUpOutput signUpInstaUser(@RequestBody UserRequest userRequest) throws NoSuchAlgorithmException {

        return userService.signUpUser(userRequest);
    }
    @PostMapping("user/signIn")
    public SignInOutput sigInInstaUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutInstaUser(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }

}
