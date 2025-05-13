package com.coding.backend.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordChangeRequestDto {
    private String currentPassword;
    private String newPassword;
}
