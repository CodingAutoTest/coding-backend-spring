package com.coding.backend.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileModifyRequestDto {
    private String name;
    private String profileImageUrl;
    private String backgroundImageUrl;
}
