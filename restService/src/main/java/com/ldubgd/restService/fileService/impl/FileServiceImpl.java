package com.ldubgd.restService.fileService.impl;

import com.ldubgd.restService.dao.JpaAppDocumentRepository;
import com.ldubgd.restService.entity.FileInfo;
import com.ldubgd.restService.fileService.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileServiceImpl implements FileService {

    private final JpaAppDocumentRepository jpaAppDocumentRepository;

    public FileServiceImpl(JpaAppDocumentRepository jpaAppDocumentRepository) {
        this.jpaAppDocumentRepository = jpaAppDocumentRepository;
    }

    @Override
    public FileInfo getFile(String docId) {
        return jpaAppDocumentRepository.findById(Long.parseLong(docId)).orElse(null);
    }


    @Override
    public FileSystemResource getFileSystemResource(FileInfo fileInfo) {
        try {
            // Створення тимчасового файлу
            File tempFile = File.createTempFile(fileInfo.getFileName(), ".tmp");
            tempFile.deleteOnExit(); // Видалити файл після завершення програми

            // Запис вмісту в тимчасовий файл
            Files.write(tempFile.toPath(), fileInfo.getFileData().getData());

            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file", e);
        }
    }
}