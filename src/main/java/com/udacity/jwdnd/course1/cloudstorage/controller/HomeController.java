package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService)
    {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionServiceDto() {
        return new EncryptionService();
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model)
    {
        int userId = userService.getUser(authentication.getName()).getUserId();

        //retrieve data
        model.addAttribute("files", fileService.getAllFilesForUserId(userId));
        model.addAttribute("notes", noteService.getAllNotesForUserId(userId));
        model.addAttribute("credentials", credentialService.getAllCredentialsForUserId(userId));

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

        if(note.getDescription().length() > 1000)
        {
            model.addAttribute("errorMessage", "Description character limit is 1000!");
        }
        else
        {
            if(note.getId() != null)
            {
                noteService.updateNote(note);
            }
            else
            {
                noteService.createNote(note,userId);
            }
        }

        return "result";
    }

    @PostMapping("/credential-upload")
    public String uploadCredential(@ModelAttribute("credential") Credential credential, Model model, Authentication authentication)
    {
        int userId = userService.getUserId(authentication.getName());

        try
        {
            credentialService.createCredential(credential, userId);
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

    @GetMapping("/note-delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication)
    {
        noteService.deleteNote(noteId, userService.getUserId(authentication.getName()));
        return "result";
    }

    @GetMapping("/credential-delete/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer credentialid, Authentication authentication)
    {
        credentialService.deleteCredential(credentialid, userService.getUserId(authentication.getName()));
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
