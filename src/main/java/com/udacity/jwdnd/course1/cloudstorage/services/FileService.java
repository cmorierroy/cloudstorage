package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService
{
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper)
    {
        this.fileMapper = fileMapper;
    }

    public int createFile(MultipartFile file, int userId) throws IOException
    {
        String fileName = file.getOriginalFilename();

        //if file is found, it already exists - throw exception
        if(fileName == "")
        {
            throw new IOException("Cannot add empty file!");
        }
        else if(getFileForUserIdByFileName(fileName, userId) != null)
        {
            throw new IOException("File name already exists!");
        }
        else //create new file!
        {
            File newFile = new File();

            newFile.setFileName(fileName);
            newFile.setContentType(file.getContentType());
            newFile.setFileSize(file.getSize() + "");
            newFile.setUserId(userId);
            newFile.setFileData(file.getBytes());

            return fileMapper.insert(newFile);
        }
    }

    public int deleteFile(String fileName, int userId)
    {
        return fileMapper.delete(fileName, userId);
    }

    public List<File> getAllFilesForUserId(int userId)
    {
        return fileMapper.getALlFilesForUserId(userId);
    }

    public File getFileForUserIdByFileName(String fileName, int userId)
    {
        return fileMapper.getFileForUserIdByFileName(fileName, userId);
    }

}
