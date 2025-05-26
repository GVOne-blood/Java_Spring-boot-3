package com.example.GVOne_blood.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generateToken(UserDetails user);

    public String generateRefreshToken(UserDetails user);

    public String extractUsername(String token);

    public boolean isTokenExpired(String token, UserDetails user);


}
