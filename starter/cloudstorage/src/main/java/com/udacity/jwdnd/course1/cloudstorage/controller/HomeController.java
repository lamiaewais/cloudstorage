package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
        model.addAttribute("files", filesService.getFilesByUserId(user.getUserId()));
        return "home";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        if (fileUpload == null || Objects.requireNonNull(fileUpload.getOriginalFilename()).isEmpty() ) {
            return "home";
        } else  if (fileUpload.getSize() >= 1048576) {
           // logger.debug("File is too large");
       //     return "home";
        }

        User user = usersService.getUser(authentication.getName());
        if (user != null) {
            File file = new File(
                    null,
                    fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    user.getUserId(),
                    fileUpload.getBytes()
            );

            int  id = filesService.insertFile(file);
            model.addAttribute("files", filesService.getFilesByUserId(user.getUserId()));
            logger.debug("Insert file with id: " + id);
        } else {
            logger.debug("user not exist");
        }

        return "home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable int fileId) {
        File file = filesService.getFileById(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFileData());

    }
}
