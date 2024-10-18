package com.ldubgd.restService.controller;

import com.ldubgd.restService.entity.FileInfo;
import com.ldubgd.restService.fileService.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequestMapping("/file")
@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    /////TODO  ПОБАВИТИСЬІ ІЗ РЕКВЕСТАМИ
    @GetMapping("/get-doc")
    public ResponseEntity<FileSystemResource> getDoc(@RequestParam("id") String hashId) {
        FileInfo doc = fileService.getFile(hashId);
        if (doc == null) {
            return ResponseEntity.badRequest().body(null);
        }

        FileSystemResource fileSystemResource = fileService.getFileSystemResource(doc);
        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }

        // Кодуємо ім'я файлу для заголовка Content-Disposition
        String encodedFilename;
        try {
            encodedFilename = URLEncoder.encode(doc.getFileName(), StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(doc.getFileType()));
        headers.setContentDispositionFormData("attachment", encodedFilename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileSystemResource);
    }
}
