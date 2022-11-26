package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public List<File> getFilesByUserId(int userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public int deleteFileById(int fileId) {
        return fileMapper.deleteFileById(fileId);
    }

    public File getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public File getFileByFileName(String filename) {
        return fileMapper.getFileByFileName(filename);
    }
}
