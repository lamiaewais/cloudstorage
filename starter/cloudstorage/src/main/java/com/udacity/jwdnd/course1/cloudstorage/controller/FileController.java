package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/home")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        boolean isFileExist = fileService.getFileByFileName(fileUpload.getOriginalFilename()) != null;
        if (isFileExist) {
            model.addAttribute("isError", true);
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

            int  id = fileService.insertFile(file);
            logger.debug("Insert file with id: " + id);
        }

        model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
        return "home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int fileId) {
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFileData());

    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable int fileId, Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        int numberOfDeletedFiles = fileService.deleteFileById(fileId);
        if (numberOfDeletedFiles == 1) {
            model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
        }

        return "home";
    }
}
