package org.example.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void init() throws IOException;
    String saveFile(MultipartFile file) throws IOException;
    String saveImage(MultipartFile file, FileSaveFormat format) throws IOException;
    void deleteImage(String imageName) throws IOException;
    String saveImage(String fileUrl, FileSaveFormat format) throws IOException;
}
