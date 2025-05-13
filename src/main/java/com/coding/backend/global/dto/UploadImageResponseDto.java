package com.coding.backend.global.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadImageResponseDto {
    private String profileImageUrl;
    private String backgroundImageUrl;
}