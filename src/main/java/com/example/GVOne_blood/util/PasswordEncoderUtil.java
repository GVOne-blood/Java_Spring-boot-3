package com.example.GVOne_blood.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PasswordEncoderUtil {

    public static String encode(String password) {
        // Mã hóa mật khẩu bằng thuật toán BCrypt

       PasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }

}
