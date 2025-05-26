package com.example.GVOne_blood.service.impl;

import com.example.GVOne_blood.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiryTime}")
    private String expiryTime;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.expiryDay}")
    private String expiryDay;
    @Override
    public String generateToken(UserDetails user) {
        // Thực hiện logic tạo token JWT ở đây
        return generateToken(Map.of(), user);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateRefreshToken(Map.of(), user);
    }


    private String generateToken(Map<String, Object> claims, @NotNull UserDetails user){
        // Thực hiện logic tạo token JWT với các claims ở đây
        return Jwts.builder()
                .setClaims(claims) // Thay thế bằng các claims thực tế
                .setSubject(user.getUsername()) // Thay thế bằng thông tin người dùng thực tế
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thay thế bằng thời gian phát hành thực tế
                .setExpiration(new Date (System.currentTimeMillis() + Long.parseLong(expiryTime) )) // Thay thế bằng thời gian hết hạn thực tế
                .signWith(getKey(), SignatureAlgorithm.HS256) // Thay thế bằng thuật toán mã hóa thực tế
                .compact(); // Trả về token JWT đã tạo
    }

    private String generateRefreshToken(Map<String, Object> claims, @NotNull UserDetails user){
        // Thực hiện logic tạo token JWT với các claims ở đây
        return Jwts.builder()
                .setClaims(claims) // Thay thế bằng các claims thực tế
                .setSubject(user.getUsername()) // Thay thế bằng thông tin người dùng thực tế
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thay thế bằng thời gian phát hành thực tế
                .setExpiration(new Date (System.currentTimeMillis() + Long.parseLong(expiryTime) * Integer.parseInt(expiryDay))) // Thay thế bằng thời gian hết hạn thực tế
                .signWith(getKey(), SignatureAlgorithm.HS256) // Thay thế bằng thuật toán mã hóa thực tế
                .compact(); // Trả về token JWT đã tạo
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Chuyển đổi chuỗi bí mật thành mảng byte
        return Keys.hmacShaKeyFor(keyBytes); // Tạo khóa HMAC từ mảng byte
    }

    @Override
    public String extractUsername(String token) {
       return extractClaim(token, Claims::getSubject); // Trích xuất tên người dùng từ token JWT
    }

    @Override
    public boolean isTokenExpired(String token, UserDetails user) {
        String username = extractUsername(token); // Trích xuất tên người dùng từ token JWT

        return username.equals(user.getUsername());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token); // Phân tích token JWT để lấy các claims
        return claimsResolver.apply(claims); // Áp dụng hàm claimsResolver vào các claims đã phân tích
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // Tạo một trình phân tích token JWT
                .setSigningKey(getKey()) // Thiết lập khóa ký
                .build()
                .parseClaimsJws(token) // Phân tích token JWT
                .getBody(); // Lấy phần thân của token
    }
}
