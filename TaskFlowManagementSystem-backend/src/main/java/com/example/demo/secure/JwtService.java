package com.example.demo.secure;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.RoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    @Autowired
    private KeyInitializer keyInitializer;
       

    /**
     * 生成一般登入用的 JWT Token (效期 24 小時)
     */
    public String generateJwtToken(UserDto userDto) {
        Date now = new Date();
        // 2026 建議：效期可根據需求調整，這裡維持 24 小時
        Date expiryDate = new Date(now.getTime() + 1000L * 60 * 60 * 24); 

        PrivateKey privateKey = keyInitializer.getKeyPair().getPrivate();

        return Jwts.builder()
                .subject(String.valueOf(userDto.getId())) // Subject 存放 User ID
                .claims(buildClaims(userDto))             // 放入自定義資訊
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(privateKey)                     // JJWT 0.12+ 會自動根據 Key 類型選擇 RS256
                .compact();
    }
    
    /**
     * 建立 Claims 內容
     */
    private Map<String, Object> buildClaims(UserDto dto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", dto.getId());
        claims.put("username", dto.getUsername()); // 建議也放入帳號，前端顯示較方便
        claims.put("fullName", dto.getFullName());
        claims.put("role", dto.getRoleName()); 
        claims.put("active", dto.getActive()); 
        return claims;
    }
    //以下暫時用不到 驗證碼的
//    /**
//     * 生成極短效的驗證碼 Token (效期 1 分鐘)
//     */
//    public String generateAuthJwtToken(String code) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + 60 * 1000); // 1 分鐘
//        
//        PrivateKey privateKey = keyInitializer.getKeyPair().getPrivate();
//
//        return Jwts.builder()
//                .subject("captcha")
//                .claim("code", code)
//                .issuedAt(now)
//                .expiration(expiryDate)
//                .signWith(privateKey)
//                .compact();
//    }
//    
//    /**
//     * 解析驗證碼 Token
//     */
//    public String parseCaptchaJwtToken(String token) {
//        PublicKey publicKey = keyInitializer.getKeyPair().getPublic();
//
//        // JJWT 0.12+ 新版 Parser 寫法
//        Claims claims = Jwts.parser()
//                .verifyWith(publicKey) // 替換舊版的 setSigningKey
//                .build()
//                .parseSignedClaims(token) // 替換舊版的 parseClaimsJws
//                .getPayload(); // 替換舊版的 getBody
//
//        return claims.get("code", String.class);
//    }
}