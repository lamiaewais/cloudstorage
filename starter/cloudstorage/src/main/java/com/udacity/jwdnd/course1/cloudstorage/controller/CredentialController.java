package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.NoteData;
import com.udacity.jwdnd.course1.cloudstorage.formdata.CredentialData;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/credential")
    public String getNotePage(
            @ModelAttribute("credentialData") CredentialData credentialData,
            Authentication authentication,
            Model model,
            @ModelAttribute("noteModal") NoteData noteData
    ) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        model.addAttribute("isCredential", true);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping("/credential")
    public String postNote(
            @ModelAttribute("credentialData") CredentialData credentialData,
            Authentication authentication,
            Model model,
            @ModelAttribute("noteModal") NoteData noteData
    ) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        Credential credential = new Credential(
                null, credentialData.getUrl(),
                credentialData.getUsername(),
                "", credentialData.getPassword(),
                user.getUserId()
        );
        if (credentialData.getCredentialId().isEmpty()) {
            credentialService.insertCredential(credential);
        } else  {
            credential.setCredentialId(Integer.parseInt(credentialData.getCredentialId()));
            credentialService.updateCredential(credential);
        }

        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        model.addAttribute("isCredential", true);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteNote(
            @PathVariable int credentialId,
            @ModelAttribute("credentialData") CredentialData credentialData,
            Authentication authentication, Model model,
            @ModelAttribute("noteModal") NoteData noteData
    ) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        credentialService.deleteCredential(credentialId);
        model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
        model.addAttribute("isCredential", true);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
