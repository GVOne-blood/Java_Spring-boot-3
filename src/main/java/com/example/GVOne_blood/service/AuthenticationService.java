package com.example.GVOne_blood.service;

import com.example.GVOne_blood.dto.request.SignInRequest;
import com.example.GVOne_blood.dto.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse authenticate(SignInRequest request);
}
