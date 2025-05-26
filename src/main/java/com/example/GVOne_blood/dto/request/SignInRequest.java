package com.example.GVOne_blood.dto.request;

import com.example.GVOne_blood.util.EnumPattern;
import com.example.GVOne_blood.util.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
public class SignInRequest implements Serializable {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @EnumPattern(name = "platform", regexp = "WEB|IOS|ANDROID|DESKTOP|OTHER", message = "Platform must be one of the following: WEB, IOS, ANDROID, DESKTOP, OTHER")
    private Platform platform;

    private String deviceToken; // Mã thiết bị để xác thực người dùng trên thiết bị di động (mobile)

}
