package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController
{
    private UserService userService;
    private FileService fileService;

    public HomeController(UserService userService, FileService fileService)
    {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model)
    {
        int userId = userService.getUser(authentication.getName()).getUserId();

        //retrieve data
        model.addAttribute("files", fileService.getAllFilesForUserId(userId));

        return "home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication)
    {
        int userId = userService.getUserId(authentication.getName()); // change to current user id (through model or authentication object)

        try
        {
            fileService.createFile(file, userId);
        }
        catch(IOException e)
        {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "result";
    }

    @GetMapping("/file-delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName, Authentication authentication)
    {
        fileService.deleteFile(fileName, userService.getUserId(authentication.getName()));
        return "result";
    }

    @GetMapping("/file-download/{fileName}")
    public ResponseEntity downloadFile(@PathVariable("fileName") String fileName, Authentication authentication)
    {
        File file = fileService.getFileForUserIdByFileName(fileName, userService.getUserId(authentication.getName()));

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }
}
