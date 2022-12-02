package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.NoteData;
import com.udacity.jwdnd.course1.cloudstorage.formdata.CredentialData;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/home")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/note")
    public String getNotePage(
            @ModelAttribute("noteModal") NoteData noteData,
            Authentication authentication,
            Model model,
            @ModelAttribute("credentialData") CredentialData credentialData
    ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
                model.addAttribute("isNote", true);
                return "home";
            }
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/note")
    public RedirectView postNote(
            @ModelAttribute("noteModal") NoteData noteData,
            Authentication authentication, Model model,
            @ModelAttribute("credentialData") CredentialData credentialData,
            RedirectAttributes redirectAttributes
            ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                RedirectView redirectView = new RedirectView("/login", true);
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
                return redirectView;
            } else {
                RedirectView redirectView = new RedirectView("/home", true);
                Note note = new Note(noteData.getNoteTitle(), noteData.getNoteDescription(), String.valueOf(user.getUserId()), null);
                if (noteData.getNoteId().isEmpty()) {
                    int numOfRowsAffected = noteService.insertNote(note);
                    if (numOfRowsAffected == 1) {
                        redirectAttributes.addFlashAttribute("isSuccessNote", true);
                        redirectAttributes.addFlashAttribute("successMessage", "Note Added Successfully");
                    } else {
                        redirectAttributes.addFlashAttribute("isErrorNote", true);
                        redirectAttributes.addFlashAttribute("errorMessage", "Sorry, something went wrong!");
                    }
                } else  {
                    note.setNoteId(Integer.parseInt(noteData.getNoteId()));
                    int numOfRowsAffected = noteService.updateNote(note);
                    if (numOfRowsAffected == 1) {
                        redirectAttributes.addFlashAttribute("isSuccessNote", true);
                        redirectAttributes.addFlashAttribute("successMessage", "Note Updated Successfully");
                    } else  {
                        redirectAttributes.addFlashAttribute("isError", true);
                        redirectAttributes.addFlashAttribute("errorMessage", "Sorry, something went wrong!");
                    }
                }

                redirectAttributes.addFlashAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
                redirectAttributes.addFlashAttribute("isNote", true);
                return redirectView;
            }
        } else {
            return new RedirectView("/login", true);
        }
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(
            @PathVariable int noteId,
            @ModelAttribute("noteModal") NoteData noteData,
            Authentication authentication,
            Model model,
            @ModelAttribute("credentialData") CredentialData credentialData
    ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/login";
            } else {
                int numOfRowsAffected = noteService.deleteNoteById(noteId);
                if (numOfRowsAffected == 1) {
                    model.addAttribute("isSuccessNote", true);
                    model.addAttribute("successMessage", "Note Deleted Successfully");
                } else  {
                    model.addAttribute("isErrorNote", true);
                    model.addAttribute("errorMessage", "Sorry, something went wrong!");
                }

                model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
                model.addAttribute("isNote", true);
                return "home";
            }
        } else {
            return "redirect:/login";
        }
    }

}
