package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.NoteData;
import com.udacity.jwdnd.course1.cloudstorage.formdata.CredentialData;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(
            UserService userService,
            CredentialService credentialService,
            EncryptionService encryptionService
    ) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/credential")
    public String getCredentialPage(
            @ModelAttribute("credentialData") CredentialData credentialData,
            Authentication authentication,
            Model model,
            @ModelAttribute("noteModal") NoteData noteData
    ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/login";
            } else  {
                model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
                model.addAttribute("isCredential", true);
                model.addAttribute("encryptionService", encryptionService);
                return "home";
            }
        } else  {
            return "redirect:/login";
        }
    }

    @PostMapping("/credential")
    public String postCredential(
            @ModelAttribute("credentialData") CredentialData credentialData,
            Authentication authentication,
            Model model,
            @ModelAttribute("noteModal") NoteData noteData,
            @ModelAttribute("encryptionService") EncryptionService encryptionService
    ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/login";
            } else  {

                if (credentialData.getCredentialId().isEmpty()) {
                    Credential credential = new Credential(
                            null, credentialData.getUrl(),
                            credentialData.getUsername(),
                            "", credentialData.getPassword(),
                            user.getUserId()
                    );
                    int numOfRowsAffected = credentialService.insertCredential(credential);
                    if (numOfRowsAffected == 1) {
                        model.addAttribute("isSuccessCredential", true);
                        model.addAttribute("successMessage", "Credential Added Successfully");
                    } else  {
                        model.addAttribute("isErrorCredential", true);
                        model.addAttribute("errorMessage", "Sorry, something went wrong!");
                    }
                } else  {
                    Credential credential = credentialService.getCredentialById(Integer.parseInt(credentialData.getCredentialId()));
                    credential.setPassword(credentialData.getPassword());
                    credential.setUrl(credentialData.getUrl());
                    credential.setUsername(credentialData.getUsername());
                    int numOfRowsAffected =  credentialService.updateCredential(credential);
                    if (numOfRowsAffected == 1) {
                        model.addAttribute("isSuccessCredential", true);
                        model.addAttribute("successMessage", "Credential Updated Successfully");
                    } else  {
                        model.addAttribute("isErrorCredential", true);
                        model.addAttribute("errorMessage", "Sorry, something went wrong!");
                    }
                }

                model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
                model.addAttribute("isCredential", true);
                model.addAttribute("encryptionService", encryptionService);
                return "home";
            }

        } else  {
            return "redirect:/login";
        }
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(
            @PathVariable int credentialId,
            @ModelAttribute("credentialData") CredentialData credentialData,
            Authentication authentication, Model model,
            @ModelAttribute("noteModal") NoteData noteData
    ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                int numOfRowsAffected = credentialService.deleteCredential(credentialId);
                if (numOfRowsAffected == 1) {
                    model.addAttribute("isSuccessCredential", true);
                    model.addAttribute("successMessage", "Credential Deleted Successfully");
                } else  {
                    model.addAttribute("isErrorCredential", true);
                    model.addAttribute("errorMessage", "Sorry, something went wrong!");
                }

                model.addAttribute("credentials", credentialService.getCredentialsByUserId(user.getUserId()));
                model.addAttribute("isCredential", true);
                model.addAttribute("encryptionService", encryptionService);
                return "home";
            }

        } else {
            return "redirect:/login";
        }
    }
}
