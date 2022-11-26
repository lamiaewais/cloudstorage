package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.NoteData;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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
    private final UserService usersService;
    private final FileService filesService;
    private final NoteService noteService;

    public HomeController(UserService usersService, FileService filesService, NoteService noteService) {
        this.usersService = usersService;
        this.filesService = filesService;
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("noteModal") NoteData noteData, Model model, Authentication authentication) {
        User user = usersService.getUser(authentication.getName());
        if (user == null) {
            logger.debug("User not exist");
            return "redirect:/login";
        }

        model.addAttribute("files", filesService.getFilesByUserId(user.getUserId()));
        model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
        return "home";
    }
}
