package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.NoteData;
import com.udacity.jwdnd.course1.cloudstorage.formdata.CredentialData;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
        model.addAttribute("isNote", true);
        return "home";
    }

    @PostMapping("/note")
    public String postNote(
            @ModelAttribute("noteModal") NoteData noteData,
            Authentication authentication, Model model,
            @ModelAttribute("credentialData") CredentialData credentialData
            ) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        Note note = new Note(noteData.getNoteTitle(), noteData.getNoteDescription(), String.valueOf(user.getUserId()));
        if (noteData.getNoteId().isEmpty()) {
            noteService.insertNote(note);
        } else  {
            note.setNoteId(Integer.parseInt(noteData.getNoteId()));
            noteService.updateNote(note);
        }

        model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
        model.addAttribute("isNote", true);
        return "home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(
            @PathVariable int noteId,
            @ModelAttribute("noteModal") NoteData noteData,
            Authentication authentication,
            Model model,
            @ModelAttribute("credentialData") CredentialData credentialData
    ) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        noteService.deleteNoteById(noteId);
        model.addAttribute("notes", noteService.getNotesByUserId(user.getUserId()));
        model.addAttribute("isNote", true);
        return "home";
    }

}
