package com.coding.backend.global.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.*;
import java.util.UUID;

@Component("localUploader")
public class LocalUploader implements FileUploader {

    private final Path root = Paths.get("uploads");

    @Override
    @SneakyThrows
    public String upload(MultipartFile file, String dir) {
        Path dst = root.resolve(dir).resolve(UUID.randomUUID() + "_" + file.getOriginalFilename());
        Files.createDirectories(dst.getParent());
        file.transferTo(dst);
        return "/static/" + root.relativize(dst).toString().replace('\\','/');
    }
}
