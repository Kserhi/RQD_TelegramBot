package com.ldubgd.restService.fileService.impl;

import com.ldubgd.restService.dao.JpaAppDocumentRepository;
import com.ldubgd.restService.entity.FileInfo;
import com.ldubgd.restService.fileService.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final JpaAppDocumentRepository jpaAppDocumentRepository;

    public FileServiceImpl(JpaAppDocumentRepository jpaAppDocumentRepository) {
        this.jpaAppDocumentRepository = jpaAppDocumentRepository;
    }

    @Override
    public FileInfo getFile(String docId) {


        Optional<FileInfo> fileInfo=jpaAppDocumentRepository.findByStatementId(Long.parseLong(docId));

        if (fileInfo.isPresent()){
            return fileInfo.get();
        }else {
            throw new  RuntimeException("файл за id не знайдено");
        }



    }


    @Override
    public FileSystemResource getFileSystemResource(FileInfo fileInfo) {
        try {
            ////TODO виправити костиль із файлами шоб не зберігальсь в основній памяті
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