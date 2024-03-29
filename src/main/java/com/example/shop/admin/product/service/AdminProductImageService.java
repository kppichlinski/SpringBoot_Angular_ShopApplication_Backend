package com.example.shop.admin.product.service;

import com.example.shop.admin.common.utils.SlugifyUtil;
import com.example.shop.admin.product.util.ExistingFileRenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminProductImageService {

    @Value("${app.uploadDir}")
    private String uploadDir;
    public String uploadImage(String filename, InputStream inputStream) {
        String newFileName = SlugifyUtil.slugifyFileName(filename);
        newFileName = ExistingFileRenameUtils.renameIfExists(Path.of(uploadDir), newFileName);
        Path filePath = Paths.get(uploadDir).resolve(newFileName);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file", e);
        }

        return newFileName;
    }

    public Resource serveFiles(String filename) {
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        return fileSystemResourceLoader.getResource(uploadDir + filename);
    }
}
