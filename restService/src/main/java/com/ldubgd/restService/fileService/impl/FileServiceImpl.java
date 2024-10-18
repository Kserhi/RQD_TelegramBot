package com.ldubgd.restService.fileService.impl;

import com.ldubgd.restService.dao.JpaAppDocumentRepository;
import com.ldubgd.restService.entity.FileInfo;
import com.ldubgd.restService.fileService.FileService;
import com.ldubgd.utils.CryptoTool;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final JpaAppDocumentRepository jpaAppDocumentRepository;

    private final CryptoTool cryptoTool;


    @Override
    public FileInfo getFile(String hashId) {


        Long id = cryptoTool.idOf(hashId);

        Optional<FileInfo> fileInfo=jpaAppDocumentRepository.findByStatementId(id);

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