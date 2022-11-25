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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final UsersService usersService;
    private final FilesService filesService;

    public FileController(UsersService usersService, FilesService filesService) {
        this.usersService = usersService;
        this.filesService = filesService;
    }

    @PostMapping("/home")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        User user = usersService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        boolean isFileExist = filesService.getFileByFileName(fileUpload.getOriginalFilename()) != null;
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

            int  id = filesService.insertFile(file);
            logger.debug("Insert file with id: " + id);
        }

        model.addAttribute("files", filesService.getFilesByUserId(user.getUserId()));
        return "home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int fileId) {
        File file = filesService.getFileById(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFileData());

    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable int fileId, Model model, Authentication authentication) {
        User user = usersService.getUser(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        int numberOfDeletedFiles = filesService.deleteFileById(fileId);
        if (numberOfDeletedFiles == 1) {
            model.addAttribute("files", filesService.getFilesByUserId(user.getUserId()));
        }

        return "home";
    }
}
