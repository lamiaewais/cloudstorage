package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.SignupData;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    public SignupController(UserService usersService) {
        this.userService = usersService;
    }

    @GetMapping
    public String getSignupPage(@ModelAttribute("signupData") SignupData signupData, Authentication authentication) {
        if (authentication != null && !userService.isUsernameAvailable(authentication.getName())) {
            return "redirect:/home";
        }

        return "signup";
    }

    @PostMapping
    public RedirectView postSignupPage(@ModelAttribute("signupData") SignupData signupData, Model model, RedirectAttributes redirectAttributes) {
        boolean isUserNameAvailable = userService.isUsernameAvailable(signupData.getUsername());
        if (isUserNameAvailable) {
            int id = userService.addUser(signupData.getUsername(), signupData.getPassword());
            model.addAttribute("isSuccess", true);
            RedirectView redirectView = new RedirectView("/login", true);
            redirectAttributes.addFlashAttribute("isSuccess",true) ;
            logger.debug("New user created with id: " + id);
            return redirectView;
        } else  {
            logger.debug("Username already exist.");
            model.addAttribute("isError", true);
            RedirectView redirectView = new RedirectView("/signup", true);
            redirectAttributes.addFlashAttribute("isError",true) ;
            return redirectView;
        }

    }
}
