package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final UsersService usersService;
    private final FilesService filesService;

    public HomeController(UsersService usersService, FilesService filesService) {
        this.usersService = usersService;
        this.filesService = filesService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication) {
        User user = usersService.getUser(authentication.getName());
        if (user == null) {
            logger.debug("User not exist");
            return "redirect:/login";
        }

        model.addAttribute("files", filesService.getFilesByUserId(user.getUserId()));
        return "home";
    }
}
