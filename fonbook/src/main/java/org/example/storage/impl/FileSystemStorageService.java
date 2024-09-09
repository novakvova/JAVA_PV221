package org.example.storage.impl;

import net.coobird.thumbnailator.Thumbnails;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageProperties;
import org.example.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    private final int [] sizes = {32,150,300,600,1200};

    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() throws IOException {
        if(!Files.exists(rootLocation))
            Files.createDirectory(rootLocation);
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {

        String randomFileName = java.util.UUID.randomUUID().toString()+"."+getFileExtension(file);

        Path destinationFile = this.rootLocation.resolve(randomFileName).normalize().toAbsolutePath();
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return randomFileName;
    }

    @Override
    public String saveImage(MultipartFile file, FileSaveFormat format) throws IOException {
        String ext = format.name().toLowerCase();
        String randomFileName = UUID.randomUUID().toString()+"."+ext;

        var bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        for (var size : sizes) {
            String fileSave = rootLocation.toString()+"/"+size+"_"+randomFileName;
            Thumbnails.of(bufferedImage).size(size, size).outputFormat(ext).toFile(fileSave);
        }
        return randomFileName;
    }

    @Override
    public void deleteImage(String imageName) throws IOException {
        if(imageName != null && !imageName.isEmpty()){
            for(int size:sizes){
                String name = size + "_" + imageName;
                Path imagePath = this.rootLocation.resolve(name).normalize().toAbsolutePath();
                //Path imagePath = imageDirPath.resolve(name);
                try {
                    Files.deleteIfExists(imagePath);
                }
                catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public String saveImage(String fileUrl, FileSaveFormat format) throws IOException {
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return saveBufferedImage(bufferedImage, format);
        }
    }

    private String saveBufferedImage(BufferedImage bufferedImage, FileSaveFormat format)  throws IOException{
        String ext = format.name().toLowerCase();
        String randomFileName = UUID.randomUUID().toString()+"."+ext;
        for (var size : sizes) {
            String fileSave = this.rootLocation.toString() +"/"+size+"_"+randomFileName;
            Thumbnails.of(bufferedImage).size(size, size).outputFormat(ext).toFile(fileSave);
        }
        return randomFileName;
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return ""; // Return an empty string if no extension is found
    }
}
