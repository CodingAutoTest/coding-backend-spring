//package com.coding.backend.global.utils;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.net.URL;
//import java.time.Instant;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class S3Uploader {
//
//    private final S3Client s3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    /**
//     * 파일을 S3에 업로드 후 public URL 반환
//     * @param file MultipartFile
//     * @param dir  저장 디렉터리(prefix) ex) "profile"
//     */
//    public String upload(MultipartFile file, String dir) {
//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("파일이 없습니다.");
//        }
//
//        String ext = getExtension(file.getOriginalFilename());
//        String key = "%s/%s_%d.%s".formatted(dir, UUID.randomUUID(), Instant.now().toEpochMilli(), ext);
//
//        try {
//            s3Client.putObject(
//                    PutObjectRequest.builder()
//                            .bucket(bucket)
//                            .key(key)
//                            .acl("public-read")            // 공개 URL
//                            .contentType(file.getContentType())
//                            .build(),
//                    software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
//                            file.getInputStream(), file.getSize())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException("S3 업로드 오류", e);
//        }
//
//        return String.format("https://%s.s3.amazonaws.com/%s", bucket, key);
//    }
//
//    /* 확장자 추출 */
//    private String getExtension(String filename) {
//        int idx = filename.lastIndexOf('.');
//        return idx > 0 ? filename.substring(idx + 1) : "";
//    }
//}
