package com.example.GVOne_blood.controller;

import com.example.GVOne_blood.dto.request.SignInRequest;
import com.example.GVOne_blood.dto.response.TokenResponse;
import com.example.GVOne_blood.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/access")
    public ResponseEntity<TokenResponse> login(@RequestBody (required = false) SignInRequest request) {

        // Xử lý xác thực người dùng ở đây
        // Trả về token hoặc thông tin người dùng nếu xác thực thành công

        return new ResponseEntity<TokenResponse>(authenticationService.authenticate(request), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public String logout() {
        // Xử lý đăng xuất người dùng ở đây
        // Xóa token hoặc thông tin phiên làm việc
        return "Logout successful";
    }

    @PostMapping("/refresh")
    public String refresh() { // lam moi token tranh viec dang nhap lai khi token het han
        // Xử lý làm mới token ở đây
        // Trả về token mới nếu làm mới thành công
        return "Token refreshed";
    }

}
