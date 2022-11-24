package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.SignupData;
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
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @GetMapping
    public String getSignupPage(@ModelAttribute("signupData") SignupData signupData) {
        return "signup";
    }

    @PostMapping
    public String postSignupPage(@ModelAttribute("signupData") SignupData signupData, Model model) {
        return "signup";
    }
}
