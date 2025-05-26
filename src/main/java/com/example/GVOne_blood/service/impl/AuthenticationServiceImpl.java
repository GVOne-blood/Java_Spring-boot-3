package com.example.GVOne_blood.service.impl;

import com.example.GVOne_blood.dto.request.SignInRequest;
import com.example.GVOne_blood.dto.response.TokenResponse;
import com.example.GVOne_blood.model.User;
import com.example.GVOne_blood.repository.UserRepository;
import com.example.GVOne_blood.service.AuthenticationService;
import com.example.GVOne_blood.service.JwtService;
import com.example.GVOne_blood.service.UserService;
import com.example.GVOne_blood.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public TokenResponse authenticate(SignInRequest request) {
       // userService.encodePassword(); // Mã hóa mật khẩu người dùng
        log.info("Authenticating user: {}", request.getUsername());
        log.info("Password: {}", request.getPassword());

//        String pass = PasswordEncoderUtil.encode(userRepository.findPasswordByUsername(request.getUsername()));
//
//        if (pass == null) {
//            log.error("User not found: {}", request.getUsername());
//            throw new UsernameNotFoundException("User not found");
//        }
//        var user_to_save = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));
//        user_to_save.setPassword(pass);
//        userRepository.save(user_to_save); // Lưu mật khẩu đã mã hóa vào cơ sở dữ liệu
//

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())); // Xác thực người dùng
        var user = userService.getByUsername(request.getUsername());


        String accessToken = jwtService.generateToken(user); // Tạo token truy cập giả lập
        String refreshToken = jwtService.generateRefreshToken(user); // Tạo token làm mới giả lập
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }
}

