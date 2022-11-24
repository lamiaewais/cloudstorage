package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.SignupData;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UsersService usersService;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    public SignupController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String getSignupPage(@ModelAttribute("signupData") SignupData signupData) {
        return "signup";
    }

    @PostMapping
    public String postSignupPage(@ModelAttribute("signupData") SignupData signupData, Model model) {
        boolean isUserNameAvailable = usersService.isUsernameAvailable(signupData.getUsername());
        if (isUserNameAvailable) {
            int id = usersService.addUser(signupData.getUsername(), signupData.getPassword());
            model.addAttribute("isSuccess", true);
            logger.debug("New user created with id: " + id);
        } else  {
            logger.debug("Username already exist.");
            model.addAttribute("isError", true);
        }

        return "signup";
    }
}
