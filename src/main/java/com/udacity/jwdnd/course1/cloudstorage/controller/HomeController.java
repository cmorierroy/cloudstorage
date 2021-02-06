package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
    private NoteService noteService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService)
    {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model)
    {
        int userId = userService.getUser(authentication.getName()).getUserId();

        //retrieve data
        model.addAttribute("files", fileService.getAllFilesForUserId(userId));
        model.addAttribute("notes", noteService.getAllNotesForUserId(userId));

        return "home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication)
    {
        int userId = userService.getUserId(authentication.getName());

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

    @PostMapping("/note-upload")
    public String uploadNote(@ModelAttribute("note") Note note, Model model, Authentication authentication)
    {
        int userId = userService.getUserId(authentication.getName());

        noteService.createNote(note,userId);

        return "result";
    }

    @GetMapping("/file-delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName, Authentication authentication)
    {
        fileService.deleteFile(fileName, userService.getUserId(authentication.getName()));
        return "result";
    }

    @GetMapping("/note-delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication)
    {
        noteService.deleteNote(noteId, userService.getUserId(authentication.getName()));
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
