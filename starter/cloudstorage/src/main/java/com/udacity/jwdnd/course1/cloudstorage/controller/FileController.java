package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.formdata.NoteData;
import com.udacity.jwdnd.course1.cloudstorage.formdata.CredentialData;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile fileUpload,
            Authentication authentication,
            Model model,
            @ModelAttribute("noteModal") NoteData noteData,
            @ModelAttribute("credentialData") CredentialData credentialData
            ) throws IOException {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/login";
            } else {
                boolean isFileExist = fileService.getFileByFileName(fileUpload.getOriginalFilename()) != null;
                if (isFileExist) {
                    model.addAttribute("isErrorFile", true);
                    model.addAttribute("errorMessage", "File already exits!");
                } else  {
                    File file = new File(
                            null,
                            fileUpload.getOriginalFilename(),
                            fileUpload.getContentType(),
                            String.valueOf(fileUpload.getSize()),
                            user.getUserId(),
                            fileUpload.getBytes()
                    );

                    int  numberOfAffectedRows = fileService.insertFile(file);
                    if (numberOfAffectedRows == 1) {
                        model.addAttribute("isSuccessFile", true);
                        model.addAttribute("successMessage", "File is Added Successfully");
                    } else  {
                        model.addAttribute("isErrorFile", true);
                        model.addAttribute("errorMessage", "Sorry, Something went wrong!");
                    }
                }

                model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
                return "home";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable int fileId,
            @ModelAttribute("noteModal") NoteData noteData,
            @ModelAttribute("credentialData") CredentialData credentialData
    ) {
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFileData());

    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(
            @PathVariable int fileId,
            Model model, Authentication authentication,
            @ModelAttribute("noteModal") NoteData noteData,
            @ModelAttribute("credentialData") CredentialData credentialData
            ) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            if (user == null) {
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/login";
            } else {
                int numberOfDeletedFiles = fileService.deleteFileById(fileId);
                if (numberOfDeletedFiles == 1) {
                    model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
                    model.addAttribute("isSuccessFile", true);
                    model.addAttribute("successMessage", "File Deleted Successfully");
                } else  {
                    model.addAttribute("isErrorFile", "Sorry, Something went wrong!!");
                }

                return "home";
            }
        } else {
            return "redirect:/login";
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadedError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "file is too large");
        return "redirect:/";
    }
}
